package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.dao.jpa.DutiesDAO;
import org.hspt.entity.jpa.QHsptDuties;
import org.hspt.entity.jpa.QHsptEmployees;
import org.hspt.entity.jpa.QHsptEmployeesDuties;
import org.hspt.entity.jpa.HsptDuties;
import org.hspt.entity.request.ReqDuties;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResDuties;
import org.hspt.service.DutiesService;

import java.util.List;

/**
 * <b> 职务基本信息实现类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class DutiesServiceImpl extends BaseService implements DutiesService {

    @Autowired
    private DutiesDAO dutiesDAO;

    @Override
    public BaseResponse addDuties(ReqDuties duties) throws BaseException {
        QHsptDuties qHsptDuties = QHsptDuties.hsptDuties;

        HsptDuties impDuties = dutiesDAO.findOne(qHsptDuties.pkGroup.isNull()
                .and(qHsptDuties.dr.eq(BaseConstants.DATA_STATUS_OK))
                .and(qHsptDuties.dutiesCode.eq(duties.getDutiesCode())));

        if (null != impDuties) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS, "职务编号不能重复");
        }
        impDuties = new HsptDuties();
        BeanUtils.copyProperties(duties, impDuties);
        impDuties.setDutiesType(0);
        impDuties = dutiesDAO.save(impDuties);

        ResDuties resDuties = new ResDuties();
        BeanUtils.copyProperties(impDuties, resDuties);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resDuties);
    }

    @Override
    public BaseResponse addDuties(ReqDuties duties, long pkGroup) throws BaseException {
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        QHsptDuties qHsptDuties = QHsptDuties.hsptDuties;

        HsptDuties impDuties = dutiesDAO.findOne(qHsptDuties.pkGroup.eq(pkGroup)
                .and(qHsptDuties.dr.eq(BaseConstants.DATA_STATUS_OK))
                .and(qHsptDuties.dutiesCode.eq(duties.getDutiesCode())));

        if (null != impDuties) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS, "职务编码已存在");
        }
        impDuties = new HsptDuties();
        BeanUtils.copyProperties(duties, impDuties);
        impDuties.setDutiesType(1);
        impDuties.setPkGroup(pkGroup);
        impDuties = dutiesDAO.save(impDuties);
        ResDuties resDuties = new ResDuties();
        BeanUtils.copyProperties(impDuties, resDuties);
        return new BaseResponse(StatusCode.ADD_SUCCESS, resDuties);
    }

    @Override
    public BaseResponse getDuties(long pkGroup, Pageable pageable, Predicate predicate) {
        QHsptDuties impDuties = QHsptDuties.hsptDuties;
        List<ResDuties> list = getQueryFactory().select(Projections.bean(
                ResDuties.class,
                impDuties.pkDuties,
                impDuties.dutiesType,
                impDuties.dutiesName,
                impDuties.dutiesCode
        )).from(impDuties).where(
                impDuties.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(impDuties.pkGroup.eq(pkGroup).and(predicate))
        ).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse<ResCount> getDutiesCount(long pkGroup, Predicate predicate) {
        QHsptDuties impDuties = QHsptDuties.hsptDuties;
        long count = getQueryFactory().select(
                impDuties.pkDuties.count()
        ).from(impDuties)
                .where(
                        impDuties.dr.eq(BaseConstants.DATA_STATUS_OK)
                                .and(impDuties.pkGroup.eq(pkGroup).and(predicate)
                                )
                ).fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse setDuties(long pkGroup, long pkDuties, ReqDuties reqDuties) throws BaseException {
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        HsptDuties duties = dutiesDAO.findOne(pkDuties);
        if (null == duties) {
            throw new BaseException(StatusCode.DATA_NOT_FOUND, "职务信息不存在");
        }
        QHsptDuties qHsptDuties = QHsptDuties.hsptDuties;
        long existsNum = getQueryFactory().selectOne().from(qHsptDuties).where(
                qHsptDuties.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qHsptDuties.dutiesCode.eq(reqDuties.getDutiesCode()))
                        .and(qHsptDuties.pkDuties.notIn(pkDuties))
        ).fetchCount();
        if (0 < existsNum) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS, "职务编码已存在");
        }
        ResDuties res = new ResDuties();
        BeanUtils.copyProperties(reqDuties, duties);
        duties = dutiesDAO.save(duties);
        BeanUtils.copyProperties(duties, res);
        return new BaseResponse(StatusCode.UPDATE_SUCCESS, res);
    }

    @Override
    public BaseResponse delDuties(long pkGroup, List<Long> pkDuties) throws BaseException {
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        QHsptEmployees qEmp = QHsptEmployees.hsptEmployees;
        QHsptEmployeesDuties qEmpDuties = QHsptEmployeesDuties.hsptEmployeesDuties;
        long existsNum = getQueryFactory().selectOne().from(qEmp, qEmpDuties).where(
                qEmp.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qEmp.pkEmployees.eq(qEmpDuties.pkEmployees))
                        .and(qEmpDuties.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(qEmpDuties.pkDuties.in(pkDuties))
        ).fetchCount();
        if (0 < existsNum) {
            throw new BaseException(StatusCode.DELETE_ERROR, "职务已绑定员工");
        }
        QHsptDuties duties = QHsptDuties.hsptDuties;
        getQueryFactory().update(duties).set(duties.dr, BaseConstants.DATA_STATUS_DEL)
                .where(duties.pkGroup.eq(pkGroup)
                        .and(duties.pkDuties.in(pkDuties))).execute();
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }
}
