package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import org.hspt.dao.jpa.*;
import org.hspt.entity.dto.*;
import org.hspt.entity.response.ResRoleMenus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.common.pub.Pub_AuthUtils;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.ReqRole;
import org.hspt.entity.request.ReqRoleMenu;
import org.hspt.entity.request.ReqRolePermission;
import org.hspt.entity.request.ReqRoleUser;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResRole;
import org.hspt.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * <b> 提供基于角色的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private PermissionsDAO permissionsDAO;

    @Autowired
    private RoleMenuDAO roleMenuDAO;

    @Autowired
    private RolePermissionDAO rolePermissionDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private Pub_AuthUtils authUtils;

    @Override
    public BaseResponse addRole(long groupId, ReqRole role) {
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(groupId);
        if (null != roleDAO.findByRoleCodeAndPkGroupAndDr(role.getRoleCode(), groupId, BaseConstants.DATA_STATUS_OK)) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptRole impRole = new HsptRole();
        BeanUtils.copyProperties(role, impRole);

        impRole.setRoleType(1);
        if (-1 == groupId) {
            impRole.setRoleType(0);
        }
        impRole.setPkGroup(groupId);
        impRole = roleDAO.save(impRole);

        ResRole resRole = new ResRole();
        BeanUtils.copyProperties(impRole, resRole);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resRole);
    }

    @Override
    public BaseResponse addRoleMenu(ReqRoleMenu roleMenuList) throws BaseException {
        List<Long> menus = new ArrayList<>();
        Long pkRole = new Long(0);
        for (RoleMenuDTO roleMenu : roleMenuList.getRoleMenus()) {
            pkRole = roleMenu.getPkRole();
            menus.add(roleMenu.getPkMenu());
        }
        addRoleMenus(pkRole, menus);
        return new BaseResponse(StatusCode.AUTH_SUCCESS);
    }

    private void addRoleMenus(Long pkRole, List<Long> roleMenuList) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        for (Long pkMenu : roleMenuList) {
            List<HsptRoleMenu> rm = getQueryFactory().selectFrom(qHsptRoleMenu).where(
                    qHsptRoleMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptRoleMenu.pkMenu.eq(pkMenu))
                            .and(qHsptRoleMenu.pkRole.eq(pkRole))
            ).fetch();
            if (null == rm || 0 == rm.size()) {
                List<HsptMenu> menus = getQueryFactory().selectFrom(qHsptMenu).where(
                        qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                                .and(qHsptMenu.menuCode.startsWith(
                                        JPAExpressions.select(qHsptMenu.menuCode).from(qHsptMenu).where(
                                                qHsptMenu.pkMenu.eq(pkMenu)
                                        )
                                ))
                ).fetch();
                List<HsptRoleMenu> roleMenus = new ArrayList<>();
                for (HsptMenu menu : menus) {
                    HsptRoleMenu impRoleMenu = new HsptRoleMenu();
                    impRoleMenu.setPkMenu(menu.getPkMenu());
                    impRoleMenu.setPkRole(pkRole);
                    impRoleMenu.setDr(BaseConstants.DATA_STATUS_OK);
                    roleMenus.add(impRoleMenu);
                }
                if (0 < roleMenus.size()) {
                    roleMenuDAO.save(roleMenus);
                }
                //刷新角色的缓存信息
                authUtils.reloadByPkRole(pkRole);
            }
        }
    }

    private void addRolePermissions(Long pkRole, List<Long> rolePermissionList) throws BaseException {
        QHsptPermissions qHsptPermission = QHsptPermissions.hsptPermissions;
        QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        for (Long pkPermission : rolePermissionList) {
            List<HsptRolePermission> rp = getQueryFactory().selectFrom(qHsptRolePermission).where(
                    qHsptRolePermission.dr.eq(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptRolePermission.pkPermission.eq(pkPermission))
                            .and(qHsptRolePermission.pkRole.eq(pkRole))
            ).fetch();
            if (null == rp || 0 == rp.size()) {
                List<HsptPermissions> permissions = getQueryFactory().selectFrom(qHsptPermission)
                        .where(qHsptPermission.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .fetch();
                List<HsptRolePermission> rolePermissions = new ArrayList<>();
                for (HsptPermissions permission : permissions) {
                    HsptRolePermission impRolePermission = new HsptRolePermission();
                    impRolePermission.setPkPermission(permission.getPkPermissions());
                    impRolePermission.setDr(BaseConstants.DATA_STATUS_OK);
                    impRolePermission.setPkRole(pkRole);
                    rolePermissions.add(impRolePermission);
                }
                if (0 < rolePermissions.size()) {
                    rolePermissionDAO.save(rolePermissions);
                }
                //刷新角色的缓存信息
                authUtils.reloadByPkRole(pkRole);
            }
        }
    }

    @Override
    public BaseResponse createRolePermission(ReqRolePermission rolePermissions) throws BaseException {
        //1. sanity check
        //2. create role
        //3. create role-permission
        //4. create role-menu
        if (null != roleDAO.findByRoleNameAndDr(rolePermissions.getRoleName(), BaseConstants.DATA_STATUS_OK)
            || (null != roleDAO.findByRoleCodeAndDr(rolePermissions.getRoleCode(), BaseConstants.DATA_STATUS_OK)))
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);

        HsptRole impRole = new HsptRole();
        BeanUtils.copyProperties(rolePermissions, impRole);
        impRole.setRoleType(0);
        impRole.setPkGroup(new Long(-1));
        impRole = roleDAO.save(impRole);

        Long pkRole = impRole.getPkRole();
        List<Long> pkPermissions = new ArrayList<>();
        List<HsptPermissions> permissions = permissionsDAO.findByDr(BaseConstants.DATA_STATUS_OK);
        for ( HsptPermissions perm : permissions) {
            pkPermissions.add(perm.getPkPermissions());
        }
        addRolePermissions(pkRole, pkPermissions);

        List<Long> pkMenus = new ArrayList<>();
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        //List<HsptMenu> menus = menuDAO.findByDr(BaseConstants.DATA_STATUS_OK);
        List<HsptMenu> menus = getQueryFactory().select(qHsptMenu)
                .from(qHsptMenu)
                .where(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK))
                .fetch();
        for ( HsptMenu m : menus) {
            pkMenus.add(m.getPkMenu());
        }
        addRoleMenus(pkRole, pkMenus);

        //set both RolePermission and RoleMenu
        //RolePermission和RoleMenu两个表都是全连接，不要使用add和delete，只使用setDr来表示存在与删除的状态
        //先清除该role的所有权限 和 菜单(setDr=1)，再新加入相应的权限，并加入相应菜单
        //QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        QHsptMenuPermissions qHsptMenuPermissions = QHsptMenuPermissions.hsptMenuPermissions;
        //Long pkRole = rolePermissions.getPkRole();

        //先清除该role的所有权限 和 菜单(setDr=1)
        List<HsptRolePermission> rpList = rolePermissionDAO.findByPkRoleAndDr(pkRole, BaseConstants.DATA_STATUS_OK);
        for (HsptRolePermission rp : rpList) {
            rp.setDr(BaseConstants.DATA_STATUS_DEL);
        }
        List<HsptRoleMenu> rmList = roleMenuDAO.findByPkRoleAndDr(pkRole, BaseConstants.DATA_STATUS_OK);
        for (HsptRoleMenu rm : rmList) {
            rm.setDr(BaseConstants.DATA_STATUS_DEL);
        }

        //再新加入相应的权限，并加入相应菜单
        for (Long pkPermission : rolePermissions.getPermissions()) {
            HsptRolePermission rp = rolePermissionDAO.findByPkPermissionAndPkRoleAndDr(pkPermission, pkRole, BaseConstants.DATA_STATUS_DEL);
            rp.setDr(BaseConstants.DATA_STATUS_OK);

            List<HsptRoleMenu> roleMenus = getQueryFactory().select(qHsptRoleMenu)
                    .from(qHsptRoleMenu, qHsptMenuPermissions)
                    .where(qHsptMenuPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptRoleMenu.dr.eq(BaseConstants.DATA_STATUS_DEL))
                            .and(qHsptRoleMenu.pkMenu.eq(qHsptMenuPermissions.pkMenu))
                            .and(qHsptMenuPermissions.pkPermissions.eq(pkPermission))
                    )
                    .fetch();
            for(HsptRoleMenu rm : roleMenus) {
                rm.setDr(BaseConstants.DATA_STATUS_OK);
            }
            //刷新角色的缓存信息
            authUtils.reloadByPkRole(pkRole);
        }
        return new BaseResponse(StatusCode.AUTH_SUCCESS);
    }

    @Override
    public BaseResponse setRolePermission(long pkRole, ReqRolePermission rolePermissions) throws BaseException {
        //set both RolePermission and RoleMenu
        //RolePermission和RoleMenu两个表都是全连接，不要使用add和delete，只使用setDr来表示存在与删除的状态
        //先清除该role的所有权限 和 菜单(setDr=1)，再新加入相应的权限，并加入相应菜单
        //QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        QHsptMenuPermissions qHsptMenuPermissions = QHsptMenuPermissions.hsptMenuPermissions;
        //Long pkRole = rolePermissions.getPkRole();

        //先清除该role的所有权限 和 菜单(setDr=1)
        List<HsptRolePermission> rpList = rolePermissionDAO.findByPkRoleAndDr(pkRole, BaseConstants.DATA_STATUS_OK);
        for (HsptRolePermission rp : rpList) {
            rp.setDr(BaseConstants.DATA_STATUS_DEL);
        }
        List<HsptRoleMenu> rmList = roleMenuDAO.findByPkRoleAndDr(pkRole, BaseConstants.DATA_STATUS_OK);
        for (HsptRoleMenu rm : rmList) {
            rm.setDr(BaseConstants.DATA_STATUS_DEL);
        }

        //再新加入相应的权限，并加入相应菜单
        for (Long pkPermission : rolePermissions.getPermissions()) {
            HsptRolePermission rp = rolePermissionDAO.findByPkPermissionAndPkRoleAndDr(pkPermission, pkRole, BaseConstants.DATA_STATUS_DEL);
            rp.setDr(BaseConstants.DATA_STATUS_OK);

            List<HsptRoleMenu> roleMenus = getQueryFactory().select(qHsptRoleMenu)
                    .from(qHsptRoleMenu, qHsptMenuPermissions)
                    .where(qHsptMenuPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptRoleMenu.dr.eq(BaseConstants.DATA_STATUS_DEL))
                            .and(qHsptRoleMenu.pkMenu.eq(qHsptMenuPermissions.pkMenu))
                            .and(qHsptMenuPermissions.pkPermissions.eq(pkPermission))
                    )
                    .fetch();
            for(HsptRoleMenu rm : roleMenus) {
                rm.setDr(BaseConstants.DATA_STATUS_OK);
            }
            //刷新角色的缓存信息
            authUtils.reloadByPkRole(pkRole);
        }
        return new BaseResponse(StatusCode.AUTH_SUCCESS);
    }

    @Override
    public List<HsptPermissions> getRolePermissions(long pkRole) throws BaseException {
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        List<HsptPermissions> permissions = getQueryFactory().select(qHsptPermissions)
            .from(qHsptPermissions, qHsptRolePermission)
            .where(qHsptPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)
                .and(qHsptRolePermission.dr.eq(BaseConstants.DATA_STATUS_OK))
                .and(qHsptPermissions.pkPermissions.eq(qHsptRolePermission.pkPermission))
                .and(qHsptRolePermission.pkRole.eq(pkRole))
            )
            .fetch();
        return permissions;
    }

    @Override
    public BaseResponse addRoleUser(ReqRoleUser roleUserList) throws BaseException {
        for (RoleUserDTO roleUser : roleUserList.getUsers()) {
            if (null == userRoleDAO.findByPkRoleAndPkUserAndDr(roleUser.getPkRole(), roleUser.getPkUser(), BaseConstants.DATA_STATUS_OK)) {
                HsptUserRole impUserRole = new HsptUserRole();
                BeanUtils.copyProperties(roleUser, impUserRole);
                userRoleDAO.save(impUserRole);
                //刷新用户角色信息
                authUtils.reloadByPkUser(roleUser.getPkUser());
            }
        }
        return new BaseResponse(StatusCode.AUTH_SUCCESS);
    }

    @Override
    public BaseResponse getCount(long pkGroup, Predicate predicate) throws BaseException {
        QHsptRole qHsptRole = QHsptRole.hsptRole;
        long count;
        if (-1 == pkGroup) {
            count = getQueryFactory().
                    select(qHsptRole.pkRole.count())
                    .from(qHsptRole)
                    .where(qHsptRole.pkGroup.in(-1L).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)).and(qHsptRole.roleType.eq(0)).and(predicate))
                    .fetchOne();

        } else {
            checkGroupIsMy(pkGroup);
            count = getQueryFactory().
                    select(qHsptRole.pkRole.count())
                    .from(qHsptRole)
                    .where(qHsptRole.pkGroup.eq(getPkGroup()).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)).and(qHsptRole.roleType.eq(1)).and(predicate))
                    .fetchOne();
        }
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getRoleInfos() throws BaseException {
        List<HsptRole> roles = roleDAO.findByDr(BaseConstants.DATA_STATUS_OK);
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        QHsptMenuPermissions qHsptMenuPermissions = QHsptMenuPermissions.hsptMenuPermissions;
        QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        QHsptUserRole userRole = QHsptUserRole.hsptUserRole;
        List<ResRoleMenus> rms = new ArrayList<>();

        for (HsptRole role : roles) {
            ResRoleMenus rm = new ResRoleMenus();
            List<MenuPermissionsDTO> mps = new ArrayList<>();
            List<HsptMenu> menus =  getQueryFactory().
                                    select(qHsptMenu)
                                    .from(qHsptMenu, qHsptRoleMenu)
                                    .where(qHsptRoleMenu.pkRole.eq(role.getPkRole())
                                            .and(qHsptRoleMenu.pkMenu.eq(qHsptMenu.pkMenu)))
                                    .fetch();
            for (HsptMenu menu : menus) {
                MenuPermissionsDTO mp = new MenuPermissionsDTO();
                if ( "Y".equals(menu.getIsEnd()) ) {
                    mp.setMenuCode(menu.getMenuCode());
                    mp.setMenuName(menu.getMenuName());
                    mp.setMenuUrl(menu.getMenuUrl());
                    mp.setMenuType(menu.getMenuType());
                    mp.setMenuLev(menu.getMenuLev());
                    mp.setIsEnd(menu.getIsEnd());
                    mp.setPkMenu(menu.getPkMenu());
                    mp.setPkFMenu(menu.getPkFMenu());
                    List<PermissionDTO> permissions = getQueryFactory().
                                                        select(Projections.bean(
                                                                PermissionDTO.class,
                                                                qHsptPermissions.pkPermissions,
                                                                qHsptPermissions.permissionsCode,
                                                                qHsptPermissions.permissionsName,
                                                                qHsptRolePermission.dr
                                                        ))
                                                        .from(qHsptPermissions, qHsptMenuPermissions, qHsptRolePermission)
                                                        .where(qHsptMenuPermissions.pkMenu.eq(menu.getPkMenu())
                                                                .and(qHsptMenuPermissions.pkPermissions.eq(qHsptPermissions.pkPermissions))
                                                                .and(qHsptRolePermission.pkPermission.eq(qHsptPermissions.pkPermissions))
                                                                .and(qHsptRolePermission.pkRole.eq(role.getPkRole())))
                                                        .fetch();
                    mp.setPermissions(permissions);
                    mps.add(mp);
                }
            }
            //判断角色是否被用户引用
            long count = getQueryFactory().select(userRole.pkRole.count()).from(userRole).where(
                    userRole.pkRole.eq(role.getPkRole()).and(userRole.dr.eq(BaseConstants.DATA_STATUS_OK))
            ).fetchOne();
            rm.setNumUsers(count);
            rm.setRoleCode(role.getRoleCode());
            rm.setRoleInfo(role.getRoleInfo());
            rm.setRoleName(role.getRoleName());
            rm.setPkRole(role.getPkRole());
            rm.setMenuPermissions(mps);
            rms.add(rm);
        }

        return new BaseResponse(rms);
    }

    @Override
    public BaseResponse getRoleAll(long pkGroup, Predicate predicate) throws BaseException {
        QHsptRole qHsptRole = QHsptRole.hsptRole;
        if (-1 == pkGroup) {
            //-1企业默认只查
            List list = getQueryFactory()
                    .select(Projections.bean(
                            ResRole.class,
                            qHsptRole.roleCode,
                            qHsptRole.roleName,
                            qHsptRole.roleInfo,
                            qHsptRole.roleType,
                            qHsptRole.pkGroup,
                            qHsptRole.pkRole
                    ))
                    .from(qHsptRole)
                    .where(qHsptRole.pkGroup.eq(getPkGroup())
                            .and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK))
                            .and(qHsptRole.roleType.eq(0))
                            .and(qHsptRole.pkRole.notIn(377385783532716032L))
                            .and(predicate))
                    .orderBy(qHsptRole.roleCode.asc())
                    .fetch();
            //使用自定义方式查询结果
            return new BaseResponse(list);
        }
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResRole.class,
                        qHsptRole.roleCode,
                        qHsptRole.roleName,
                        qHsptRole.roleInfo,
                        qHsptRole.roleType,
                        qHsptRole.pkGroup,
                        qHsptRole.pkRole
                ))
                .from(qHsptRole)
                .where(qHsptRole.pkGroup.eq(getPkGroup()).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)).and(qHsptRole.roleType.eq(1)).and(predicate))
                .orderBy(qHsptRole.roleCode.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delRole(long roleId) throws BaseException {
        QHsptRole qHsptRole = QHsptRole.hsptRole;
        HsptRole role = roleDAO.findOne(qHsptRole.pkRole.eq(roleId).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)));
        if (null != role) {
            checkGroupIsMy(role.getPkGroup());
            //判断角色是否被用户引用
            QHsptUserRole userRole = QHsptUserRole.hsptUserRole;
            long count = getQueryFactory().select(userRole.pkRole.count()).from(userRole).where(
                    userRole.pkRole.eq(roleId).and(userRole.dr.eq(BaseConstants.DATA_STATUS_OK))
            ).fetchOne();
            if (0 < count) {
                throw new BaseException(StatusCode.DATA_QUOTE, "角色被用户引用，无法删除");
            }
            role.setDr(BaseConstants.DATA_STATUS_DEL);
            roleDAO.save(role);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse setRole(Long roleId, ReqRole reqRole) throws BaseException {
        HsptRole role = roleDAO.findOne(roleId);
        if (null != role) {
            checkGroupIsMy(role.getPkGroup());
            BeanUtils.copyProperties(reqRole, role);
            role = roleDAO.save(role);
            ResRole resRole = new ResRole();
            BeanUtils.copyProperties(role, resRole);

            return new BaseResponse(StatusCode.ADD_SUCCESS, resRole);
        }
        return new BaseResponse(StatusCode.DATA_NOT_FOUND);
    }


    @Override
    public BaseResponse getRoles(long pkGroup, Pageable pageable, Predicate predicate) throws BaseException {

        QHsptRole qHsptRole = QHsptRole.hsptRole;
        if (-1 == pkGroup) {
            List list = getQueryFactory()
                    .select(Projections.bean(
                            ResRole.class,
                            qHsptRole.roleCode,
                            qHsptRole.roleName,
                            qHsptRole.roleInfo,
                            qHsptRole.roleType,
                            qHsptRole.pkGroup,
                            qHsptRole.pkRole
                    ))
                    .from(qHsptRole)
                    .where(qHsptRole.pkGroup.eq(getPkGroup()).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)).and(qHsptRole.roleType.eq(0)).and(predicate))
                    .orderBy(qHsptRole.roleCode.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            return new BaseResponse(list);
        }
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResRole.class,
                        qHsptRole.roleCode,
                        qHsptRole.roleName,
                        qHsptRole.roleInfo,
                        qHsptRole.roleType,
                        qHsptRole.pkGroup,
                        qHsptRole.pkRole
                ))
                .from(qHsptRole)
                .where(qHsptRole.pkGroup.eq(getPkGroup()).and(qHsptRole.dr.eq(BaseConstants.DATA_STATUS_OK)).and(qHsptRole.roleType.eq(1)).and(predicate))
                .orderBy(qHsptRole.roleCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}
