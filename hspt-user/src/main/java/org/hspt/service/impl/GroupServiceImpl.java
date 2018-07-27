package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.common.pub.Pub_Tools;
import org.hspt.dao.jpa.GroupDAO;
import org.hspt.dao.jpa.OrgDAO;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.ReqGroup;
import org.hspt.entity.request.ReqOrg;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResGroup;
import org.hspt.service.GroupService;
import org.hspt.service.OrgService;

import java.util.List;

/**
 * <b> 提供基于组织的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class GroupServiceImpl extends BaseService implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private OrgDAO orgDAO;


    @Autowired
    private Pub_Tools pubTools;

    @Autowired
    private OrgService orgService;

    @Override
    public BaseResponse addGroup(ReqGroup group) throws BaseException {
        if (null != groupDAO.findByGroupCodeAndDr(group.getGroupCode(), BaseConstants.DATA_STATUS_OK)) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptGroup impGroup = new HsptGroup();
        BeanUtils.copyProperties(group, impGroup);
        impGroup = groupDAO.save(impGroup);

        //生成组织初始的机构节点档案
        ReqOrg org = new ReqOrg();
        org.setOrgCode(impGroup.getGroupCode());
        org.setOrgName(impGroup.getGroupName());
        org.setNodetype(0);
        org.setPkFOrg(-1L);
        org.setOrgLev(0);
        org.setOrderCode("" + pubTools.genUUID());
        org.setPlatform("Web");
        org.setVersion("v01");
        BaseResponse response = orgService.addOrg(org, impGroup.getPkGroup());
        if (BaseStatus.OK.getCode() == response.getStatus()) {
            if (StatusCode.ADD_SUCCESS.getCode() == response.getCode()) {

                ResGroup resGroup = new ResGroup();
                BeanUtils.copyProperties(impGroup, resGroup);

                return new BaseResponse(StatusCode.ADD_SUCCESS, resGroup);
            }
        }

        //如果机构生成失败，则删除已经添加的机构档案，客户端重新提交创建
        groupDAO.delete(impGroup.getPkGroup());

        return new BaseResponse(StatusCode.ADD_ERROR);

    }

    @Override
    public BaseResponse setGroup(Long pkGroup, ReqGroup reqGroup) throws BaseException {
        if (-1 != getPkGroup()) {
            checkGroupIsMy(pkGroup);
        }
        HsptGroup group = groupDAO.findOne(pkGroup);
        if (null == group) {
            throw new BaseException(StatusCode.DATA_NOT_FOUND);
        }

        if (-1 != getPkGroup()) {
            QHsptOrg qHsptOrg = QHsptOrg.hsptOrg;
            HsptOrg org = orgDAO.findOne(qHsptOrg.pkGroup.eq(pkGroup).and(qHsptOrg.pkFOrg.eq(-1L)));
            if (null != org) {
                org.setOrgCode(reqGroup.getGroupCode());
                org.setOrgName(reqGroup.getGroupName());
                orgDAO.save(org);
            }
        }

        BeanUtils.copyProperties(reqGroup, group);
        group = groupDAO.save(group);
        //更新组织对应的机构
        HsptOrg org = orgDAO.findByPkOrgAndDr(group.getPkGroup(), BaseConstants.DATA_STATUS_OK);
        org.setOrgCode(group.getGroupCode());
        org.setOrgName(group.getGroupName());
        orgService.setOrg(org);

        ResGroup resGroup = new ResGroup();
        BeanUtils.copyProperties(group, resGroup);
        return new BaseResponse(StatusCode.UPDATE_SUCCESS, resGroup);
    }

    @Override
    public BaseResponse delGroup(Long pkGroup) throws BaseException {
        if (-1 != getPkGroup()) {
            throw new BaseException(StatusCode.UNAUTHORIZED_OPERATION);
        }

        HsptGroup group = groupDAO.findOne(pkGroup);
        if (null == group) {
            throw new BaseException(StatusCode.DATA_NOT_FOUND);
        }

        //如果系统已经有管理员则 不允许删除
        QHsptUser qHsptUser = QHsptUser.hsptUser;

        if (0 < getQueryFactory()
                .select(qHsptUser.pkUser.count())
                .from(qHsptUser)
                .where(qHsptUser.pkGroup.eq(pkGroup)
                        .and(qHsptUser.dr.eq(BaseConstants.DATA_STATUS_OK)))
                .fetchOne()) {
            throw new BaseException(StatusCode.DATA_QUOTE, "组织下已存在组织管理员");
        }
        //易电务组织下没有机构
        if (-1 != group.getPkGroup()) {
            orgService.delRootOrg(group.getPkGroup());
        }


        group.setDr(BaseConstants.DATA_STATUS_DEL);
        groupDAO.save(group);
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse getGroupList(Pageable pageable, Predicate predicate) throws BaseException {
        QHsptGroup qHsptGroup = QHsptGroup.hsptGroup;
        List<ResGroup> list = getQueryFactory().select(Projections.bean(
                ResGroup.class,
                qHsptGroup.pkGroup,
                qHsptGroup.groupCode,
                qHsptGroup.groupName,
                qHsptGroup.groupType
        )).from(qHsptGroup)
                .where(qHsptGroup.dr.eq(BaseConstants.DATA_STATUS_OK).and(predicate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse getGroupCount(Predicate predicate) throws BaseException {
        QHsptGroup qHsptGroup = QHsptGroup.hsptGroup;
        long countNum = getQueryFactory().select(qHsptGroup.pkGroup.count()).from(qHsptGroup)
                .where(qHsptGroup.dr.eq(BaseConstants.DATA_STATUS_OK).and(predicate))
                .fetchOne();
        return new BaseResponse(new ResCount(countNum));
    }


    @Override
    public BaseResponse<List<ResGroup>> getGroupCombobox() {
        QHsptGroup qHsptGroup = QHsptGroup.hsptGroup;
        List<ResGroup> res;
        if (-1 == getPkGroup()) {
            res = getQueryFactory().select(Projections.bean(
                    ResGroup.class,
                    qHsptGroup.pkGroup,
                    qHsptGroup.groupCode,
                    qHsptGroup.groupName,
                    qHsptGroup.groupType
            )).from(qHsptGroup)
                    .where(
                            qHsptGroup.dr.eq(BaseConstants.DATA_STATUS_OK)
                    )
                    .fetch();
        } else {
            //普华讯光组织
            long PHXG_GROUP = 400670264993513472L;
            res = getQueryFactory().select(Projections.bean(
                    ResGroup.class,
                    qHsptGroup.pkGroup,
                    qHsptGroup.groupCode,
                    qHsptGroup.groupName,
                    qHsptGroup.groupType
            )).from(qHsptGroup)
                    .where(
                            qHsptGroup.dr.eq(BaseConstants.DATA_STATUS_OK)
                                    .and(qHsptGroup.pkGroup.in(getPkGroup(), PHXG_GROUP))
                    )
                    .fetch();
        }
        return new BaseResponse<>(res);
    }


}
