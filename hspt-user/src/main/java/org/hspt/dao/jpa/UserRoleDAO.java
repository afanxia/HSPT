package org.hspt.dao.jpa;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptUserRole;

/**
 * <b> 用户角色操作DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "user_roles")
public interface UserRoleDAO extends BaseJpaDAO<HsptUserRole> {


    /**
     * 根据角色主键与用户主键获取信息
     *
     * @param pkRole 角色主键
     * @param pkUser 用户主键
     * @param dr     删除标识
     * @return
     */
    @Cacheable(key = "#p0+#p1+#p2")
    HsptUserRole findByPkRoleAndPkUserAndDr(long pkRole, long pkUser, int dr);


    /**
     * 按照用户查询UserRole
     *
     * @param userCode
     * @param dr
     * @return
     */
    HsptUserRole findByPkUserAndDr(long pkUser, int dr);
}
