package org.hspt.service.impl;

import com.querydsl.core.types.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.common.pub.Pub_AuthUtils;
import org.hspt.dao.jpa.MenuPermissionsDAO;
import org.hspt.entity.dto.MenuAuthDTO;
import org.hspt.entity.jpa.QHsptMenuPermissions;
import org.hspt.entity.jpa.QHsptPermissions;
import org.hspt.entity.jpa.HsptMenuPermissions;
import org.hspt.entity.request.ReqMenuAuth;
import org.hspt.entity.response.ResPermissions;
import org.hspt.service.MenuAuthService;

import java.util.List;

/**
 * <b> 菜单授权 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class MenuAuthServiceImpl extends BaseService implements MenuAuthService {

    @Autowired
    private MenuPermissionsDAO menuPermissionsDAO;

    @Autowired
    private Pub_AuthUtils authUtils;

    @Override
    public BaseResponse addMenuAuth(ReqMenuAuth reqMenuAuth) throws BaseException {
        for (MenuAuthDTO menuAuth : reqMenuAuth.getMenuAuths()) {
            if (null == menuPermissionsDAO.findByPkMenuAndPkPermissionsAndDr(menuAuth.getPkMenu(), menuAuth.getPkPermissions(), BaseConstants.DATA_STATUS_OK)) {
                HsptMenuPermissions impMenuPermissions = new HsptMenuPermissions();
                BeanUtils.copyProperties(menuAuth, impMenuPermissions);
                menuPermissionsDAO.save(impMenuPermissions);

                //刷新菜单的授权信息
                authUtils.reloadByPkMenu(menuAuth.getPkMenu());
            }
        }
        return new BaseResponse(StatusCode.AUTH_SUCCESS);
    }

    @Override
    public BaseResponse delMenuAuth(long menuPermissionsId) throws BaseException {
        HsptMenuPermissions impMenuPermissions = menuPermissionsDAO.findOne(menuPermissionsId);
        if (null != impMenuPermissions) {
            impMenuPermissions.setDr(BaseConstants.DATA_STATUS_DEL);
            menuPermissionsDAO.save(impMenuPermissions);

            //刷新菜单的授权信息
            authUtils.reloadByPkMenu(impMenuPermissions.getPkMenu());
        }

        return new BaseResponse(StatusCode.UNAUTH_SUCCESS);
    }

    @Override
    public BaseResponse getMenuAuthList(long menuId) throws BaseException {

        QHsptPermissions permissions = QHsptPermissions.hsptPermissions;

        QHsptMenuPermissions menuPermissions = QHsptMenuPermissions.hsptMenuPermissions;

        List<ResPermissions> list = getQueryFactory()
                .select(
                        Projections.bean(
                                ResPermissions.class,
                                permissions.pkPermissions,
                                permissions.permissionsCode,
                                permissions.permissionsName,
                                permissions.permissionsInfo
                        ))
                .from(permissions, menuPermissions)
                .where(permissions.pkPermissions.eq(menuPermissions.pkPermissions)
                        .and(menuPermissions.pkMenu.eq(menuId)))
                .fetch();

        return new BaseResponse(list);
    }
}
