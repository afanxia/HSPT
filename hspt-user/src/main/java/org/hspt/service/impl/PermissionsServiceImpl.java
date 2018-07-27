package org.hspt.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.dao.jpa.PermissionsDAO;
import org.hspt.dao.jpa.PermissionsResourceDAO;
import org.hspt.entity.jpa.QHsptPermissions;
import org.hspt.entity.jpa.HsptPermissions;
import org.hspt.entity.jpa.HsptPermissionsResource;
import org.hspt.service.PermissionsService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <b> 资源权限服务管理类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class PermissionsServiceImpl extends BaseService implements PermissionsService {

    @Autowired
    private PermissionsDAO permissionsDAO;

    @Autowired
    private PermissionsResourceDAO permissionsResourceDAO;

    @Override
    public BaseResponse getAll() throws BaseException {
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        List list = getQueryFactory().
                selectFrom(qHsptPermissions)
                .where(qHsptPermissions.pkGroup.in(-1).and(qHsptPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)))
                .orderBy(qHsptPermissions.permissionsCode.asc())
                .fetch()
                .stream()
                .map(tuple -> {
                    return getPermissionsMap(tuple);
                }).collect(Collectors.toList());
        return new BaseResponse(JSON.toJSON(list));
    }

    @Override
    public BaseResponse getPermissions(Pageable pageable) throws BaseException {
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        List list = getQueryFactory().
                selectFrom(qHsptPermissions)
                .where(qHsptPermissions.pkGroup.in(-1).and(qHsptPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)))
                .orderBy(qHsptPermissions.permissionsCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(tuple -> {
                    return getPermissionsMap(tuple);
                }).collect(Collectors.toList());
        return new BaseResponse(JSON.toJSON(list));
    }

    @Override
    public BaseResponse getPermission(long permissionId) throws BaseException {
        return new BaseResponse(permissionsDAO.findOne(permissionId));
    }

    @Override
    public BaseResponse delPermission(long permissionId) throws BaseException {

        HsptPermissions impPermissions = permissionsDAO.findOne(permissionId);
        if (null != impPermissions) {
            //删除权限资源信息
            List<HsptPermissionsResource> list = permissionsResourceDAO.findByPkPermissionsAndDr(permissionId, BaseConstants.DATA_STATUS_OK);
            for (HsptPermissionsResource resource : list) {
                resource.setDr(BaseConstants.DATA_STATUS_DEL);
            }
            permissionsResourceDAO.save(list);
            //删除权限信息
            impPermissions.setDr(BaseConstants.DATA_STATUS_DEL);
            permissionsDAO.save(impPermissions);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    /**
     * 用于构造前端返回数据，后期需要改造为实体类，此处只是为了实现写法
     *
     * @param tuple
     * @return
     */
    public Map<String, Object> getPermissionsMap(HsptPermissions tuple) {
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(qHsptPermissions.permissionsCode.getMetadata().getName(), tuple.getPermissionsCode());
        map.put(qHsptPermissions.permissionsName.getMetadata().getName(), tuple.getPermissionsName());
        map.put(qHsptPermissions.permissionsInfo.getMetadata().getName(), tuple.getPermissionsInfo());
        map.put(qHsptPermissions.pkPermissions.getMetadata().getName(), tuple.getPkPermissions());
        return map;
    }
}
