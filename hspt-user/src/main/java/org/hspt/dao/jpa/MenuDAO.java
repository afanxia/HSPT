package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.QHsptMenu;
import org.hspt.entity.jpa.HsptMenu;

import java.util.List;

/**
 * <b> 菜单管理数据库操作DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Repository
@CacheConfig(cacheNames = "menus")
public interface MenuDAO extends BaseJpaDAO<HsptMenu>, QuerydslBinderCustomizer<QHsptMenu> {


    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param menu
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptMenu menu) {
        bindings.bind(menu.menuCode).first(StringExpression::containsIgnoreCase);
        bindings.bind(menu.menuName).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 根据菜单删除状态查询菜单信息
     *
     * @param dr       删除标识
     * @return
     */
    @Cacheable(key = "#p0")
    List<HsptMenu> findByDr(int dr);


    /**
     * 根据菜单编码查询菜单信息
     *
     * @param menuCode 菜单编码
     * @param dr       删除标识
     * @return
     */
    @Cacheable(key = "#p0+#p1")
    HsptMenu findByMenuCodeAndDr(String menuCode, int dr);

    /**
     * 获取某个菜单下的所有下级菜单
     *
     * @param fmenu 上级菜单主键
     * @param dr    删除标识
     * @return
     */
    @Cacheable(key = "#p0+#p1")
    List<HsptMenu> findByPkFMenuAndDr(long fmenu, int dr);


}
