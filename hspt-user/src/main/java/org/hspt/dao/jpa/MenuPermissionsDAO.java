package org.hspt.dao.jpa;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptMenuPermissions;

/**
 * <b> 菜单授权DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "menupermissions")
public interface MenuPermissionsDAO extends BaseJpaDAO<HsptMenuPermissions> {

    /**
     * 根据权限主键获取菜单权限信息
     *
     * @param pkPermissions 权限主键
     * @param dr            删除标识
     * @return
     */
    @Cacheable(key = "#p0+#p1")
    HsptMenuPermissions findByPkPermissionsAndDr(long pkPermissions, int dr);

    /**
     * 根据菜单主键、权限主键获取菜单权限信息
     *
     * @param pkMenu        菜单主键
     * @param pkPermissions 权限主键
     * @param dr            删除标识
     * @return
     */
    @Cacheable(key = "#p0+#p1+#p2")
    HsptMenuPermissions findByPkMenuAndPkPermissionsAndDr(long pkMenu, long pkPermissions, int dr);
}
