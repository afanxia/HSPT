package org.hspt.dao.jpa;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptRoleMenu;

import java.util.List;

/**
 * <b> 角色菜单授权DAO </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "rolemenu")
public interface RoleMenuDAO extends BaseJpaDAO<HsptRoleMenu> {

    /**
     * 根据菜单主键与角色主键判断是否已经授权
     *
     * @param pkMenu 菜单主键
     * @param pkRole 角色主键
     * @param dr     删除标识
     * @return
     */
    HsptRoleMenu findByPkMenuAndPkRoleAndDr(long pkMenu, long pkRole, int dr);


    /**
     * 根据角色主键判断是否已经授权
     *
     * @param pkRole 角色主键
     * @param dr     删除标识
     * @return
     */
    List<HsptRoleMenu> findByPkRoleAndDr(long pkRole, int dr);

}
