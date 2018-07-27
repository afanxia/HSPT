package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.common.pub.Pub_AuthUtils;
import org.hspt.dao.jpa.MenuDAO;
import org.hspt.entity.dto.UserMenuDTO;
import org.hspt.entity.jpa.QHsptMenu;
import org.hspt.entity.jpa.QHsptRoleMenu;
import org.hspt.entity.jpa.HsptMenu;
import org.hspt.entity.request.ReqMenu;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResMenu;
import org.hspt.service.MenuService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <b> 菜单管理具体实现类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private Pub_AuthUtils authUtils;


    @Override
    public BaseResponse addMenu(ReqMenu menu) throws BaseException {

        if (null != menuDAO.findByMenuCodeAndDr(menu.getMenuCode(), BaseConstants.DATA_STATUS_OK)) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }

        HsptMenu impMenu = new HsptMenu();
        BeanUtils.copyProperties(menu, impMenu);
        impMenu.setIsEnd(BaseConstants.TRUE);
        if (0 == menu.getPkFMenu()) {
            impMenu.setMenuLev(1);
        } else {
            HsptMenu fmenu = menuDAO.findOne(menu.getPkFMenu());
            if (0 != menu.getPkFMenu() && null == fmenu) {
                throw new BaseException(StatusCode.MENU_F_NOT_FOUND);
            }
            //设置级别为上级级别加一
            impMenu.setMenuLev(fmenu.getMenuLev() + 1);
            //更新为非末级
            if (BaseConstants.TRUE.equals(fmenu.getIsEnd())) {
                fmenu.setIsEnd(BaseConstants.FALSE);
                menuDAO.save(fmenu);
            }
        }
        impMenu = menuDAO.save(impMenu);
        ResMenu resMenu = new ResMenu();
        if (null != impMenu) {
            BeanUtils.copyProperties(impMenu, resMenu);
        }

        return new BaseResponse(StatusCode.ADD_SUCCESS, resMenu);
    }

    @Override
    public BaseResponse setMenu(long menuId, ReqMenu menu) throws BaseException {
        HsptMenu impMenu = menuDAO.findOne(menuId);
        if (null != impMenu) {
            BeanUtils.copyProperties(menu, impMenu);
            impMenu = menuDAO.save(impMenu);
            //刷新菜单的授权信息
            authUtils.reloadByPkMenu(impMenu.getPkMenu());
        }

        ResMenu resMenu = new ResMenu();
        if (null != impMenu) {
            BeanUtils.copyProperties(impMenu, resMenu);
        }

        return new BaseResponse(StatusCode.UPDATE_SUCCESS, resMenu);
    }

    @Override
    public BaseResponse delMenu(long menuId) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        HsptMenu impMenu = menuDAO.findOne(qHsptMenu.pkMenu.eq(menuId).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)));
        if (null != impMenu) {
            //判断菜单是否被角色引用
            QHsptRoleMenu roleMenu = QHsptRoleMenu.hsptRoleMenu;
            long rms = getQueryFactory().select(roleMenu.count()).from(roleMenu).where(
                    roleMenu.pkMenu.eq(menuId).and(roleMenu.dr.eq(BaseConstants.DATA_STATUS_OK))
            ).fetchOne();
            if (0 < rms) {
                throw new BaseException(StatusCode.DATA_QUOTE, "菜单被角色引用，无法删除");
            }
            //判断菜单是否存在下级
            if (!BaseConstants.TRUE.equals(impMenu.getIsEnd())) {
                throw new BaseException(StatusCode.DATA_QUOTE, "菜单存在下级菜单，无法删除");
            }
            impMenu.setDr(BaseConstants.DATA_STATUS_DEL);
            menuDAO.save(impMenu);
            //刷新菜单的授权信息
            authUtils.reloadByPkMenu(impMenu.getPkMenu());

            //处理上级菜单是否还有末级 如果没有则将状态置为末级
            long count = menuDAO.count(qHsptMenu.pkFMenu.eq(impMenu.getPkFMenu()).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)));
            if (0 == count) {
                HsptMenu fmenu = menuDAO.findOne(impMenu.getPkFMenu());
                if (null != fmenu) {
                    fmenu.setIsEnd(BaseConstants.TRUE);
                    menuDAO.save(fmenu);
                }
            }
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse getMenu(long menuId) throws BaseException {
        HsptMenu menu = menuDAO.findOne(menuId);
        ResMenu resMenu = new ResMenu();
        if (null != menu) {
            BeanUtils.copyProperties(menu, resMenu);
        }
        return new BaseResponse(resMenu);
    }


    @Override
    public BaseResponse getCount(long pkGroup, Predicate predicate) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        long count;
        if (-1 == pkGroup) {
            count = getQueryFactory().select(qHsptMenu.pkMenu.count()).from(qHsptMenu)
                    .where(qHsptMenu.menuType.in(0, 1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate)).fetchOne();
        } else {
            //校验操作的组织是否是自己的组织
            checkGroupIsMy(pkGroup);
            count = getQueryFactory().select(qHsptMenu.pkMenu.count()).from(qHsptMenu)
                    .where(qHsptMenu.menuType.eq(1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate)).fetchOne();
        }
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse<List<ResMenu>> getMenusByRole(Long pkRole) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        List<Integer> menuType = new ArrayList<>();
        menuType.add(1);
        if (null == getPkGroup() || -1 == getPkGroup()) {
            menuType.add(0);
        }
        List<ResMenu> res = getQueryFactory().select(Projections.bean(
                ResMenu.class,
                qHsptMenu.menuCode,
                qHsptMenu.menuName,
                qHsptMenu.menuType,
                qHsptMenu.pkMenu,
                qHsptMenu.pkFMenu,
                qHsptMenu.menuUrl,
                qHsptMenu.orderCode,
                qHsptMenu.menuIco,
                qHsptMenu.isEnd

        ))
                .from(qHsptMenu, qHsptRoleMenu)
                .where(
                        qHsptMenu.menuType.in(menuType)
                                .and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK))
                                .and(qHsptMenu.pkMenu.eq(qHsptRoleMenu.pkMenu))
                                .and(qHsptRoleMenu.dr.eq(qHsptMenu.dr))
                                .and(qHsptRoleMenu.pkRole.eq(pkRole))
                )
                .orderBy(qHsptMenu.orderCode.asc())
                .fetch();
        return new BaseResponse<>(res);
    }

    @Override
    public BaseResponse<List<ResMenu>> getRoleNoMenu(Long pkRole, Long pkGroup, Pageable pageable, Predicate predicate) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        JPAQuery authMenu = getQueryFactory().select(qHsptRoleMenu.pkMenu).from(qHsptRoleMenu)
                .where(qHsptRoleMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qHsptRoleMenu.pkRole.eq(pkRole))
                );
        if (-1 == pkGroup) {
            List list = getQueryFactory().select(Projections.bean(
                    ResMenu.class,
                    qHsptMenu.menuCode,
                    qHsptMenu.menuName,
                    qHsptMenu.menuType,
                    qHsptMenu.pkMenu,
                    qHsptMenu.pkFMenu,
                    qHsptMenu.menuUrl,
                    qHsptMenu.orderCode,
                    qHsptMenu.menuIco,
                    qHsptMenu.isEnd

            ))
                    .from(qHsptMenu)
                    .where(qHsptMenu.menuType.in(0, 1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate)
                            .and(qHsptMenu.pkMenu.notIn(authMenu))
                    )
                    .orderBy(qHsptMenu.orderCode.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            //使用自定义方式查询结果
            return new BaseResponse(list);
        }
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        List list = getQueryFactory().select(Projections.bean(
                ResMenu.class,
                qHsptMenu.menuCode,
                qHsptMenu.menuName,
                qHsptMenu.menuType,
                qHsptMenu.pkFMenu,
                qHsptMenu.pkMenu,
                qHsptMenu.menuUrl,
                qHsptMenu.orderCode,
                qHsptMenu.menuIco,
                qHsptMenu.isEnd

        ))
                .from(qHsptMenu)
                .where(qHsptMenu.menuType.in(1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate)
                        .and(qHsptMenu.pkMenu.notIn(authMenu))
                )
                .orderBy(qHsptMenu.orderCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new BaseResponse<>(list);
    }

    @Override
    public BaseResponse<ResCount> cheReportMenu(Long pkTemplate) throws BaseException {
        QHsptMenu qMenu = QHsptMenu.hsptMenu;
        long res = getQueryFactory().selectFrom(qMenu).where(
                qMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qMenu.menuUrl.endsWith(pkTemplate.toString()))
        ).fetchCount();
        return new BaseResponse<>(new ResCount(res));
    }

    @Override
    public BaseResponse getMenus() throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        List list = getQueryFactory().select(Projections.bean(
                UserMenuDTO.class,
                qHsptMenu.menuCode,
                qHsptMenu.menuName,
                qHsptMenu.menuType,
                qHsptMenu.pkFMenu,
                qHsptMenu.pkMenu,
                qHsptMenu.menuUrl,
                qHsptMenu.orderCode,
                qHsptMenu.menuIco,
                qHsptMenu.menuLev,
                qHsptMenu.isEnd
        ))
                .from(qHsptMenu)
                .where(qHsptMenu.menuType.in(1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)))
                .orderBy(qHsptMenu.orderCode.asc())
                .fetch();
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse getMenus(long pkGroup, Pageable pageable, Predicate predicate) throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        if (-1 == pkGroup) {
            List list = getQueryFactory().select(Projections.bean(
                    ResMenu.class,
                    qHsptMenu.menuCode,
                    qHsptMenu.menuName,
                    qHsptMenu.menuType,
                    qHsptMenu.pkMenu,
                    qHsptMenu.pkFMenu,
                    qHsptMenu.menuUrl,
                    qHsptMenu.orderCode,
                    qHsptMenu.menuIco,
                    qHsptMenu.isEnd

            ))
                    .from(qHsptMenu)
                    .where(qHsptMenu.menuType.in(0, 1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate))
                    .orderBy(qHsptMenu.orderCode.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            //使用自定义方式查询结果
            return new BaseResponse(list);
        }
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(pkGroup);
        List list = getQueryFactory().select(Projections.bean(
                ResMenu.class,
                qHsptMenu.menuCode,
                qHsptMenu.menuName,
                qHsptMenu.menuType,
                qHsptMenu.pkFMenu,
                qHsptMenu.pkMenu,
                qHsptMenu.menuUrl,
                qHsptMenu.orderCode,
                qHsptMenu.menuIco,
                qHsptMenu.isEnd

        ))
                .from(qHsptMenu)
                .where(qHsptMenu.menuType.in(1).and(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)).and(predicate))
                .orderBy(qHsptMenu.orderCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new BaseResponse(list);
    }

    /**
     * 用于构造前端返回数据，后期需要改造为实体类，此处只是为了实现写法
     *
     * @param tuple
     * @return
     */
    public Map<String, Object> getMenuMap(HsptMenu tuple) {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(qHsptMenu.menuCode.getMetadata().getName(), tuple.getMenuCode());
        map.put(qHsptMenu.menuName.getMetadata().getName(), tuple.getMenuName());
        map.put(qHsptMenu.menuUrl.getMetadata().getName(), tuple.getMenuUrl());
        map.put(qHsptMenu.menuIco.getMetadata().getName(), tuple.getMenuIco());
        map.put(qHsptMenu.pkMenu.getMetadata().getName(), tuple.getPkMenu());
        map.put(qHsptMenu.pkFMenu.getMetadata().getName(), tuple.getPkFMenu());
        map.put(qHsptMenu.isEnd.getMetadata().getName(), tuple.getIsEnd());
        return map;
    }
}
