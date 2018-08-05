package org.hspt.dao.jpa;

import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptRole;
import org.hspt.entity.jpa.QHsptGroup;
import org.hspt.entity.jpa.QHsptRole;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b> 角色操作DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "roles")
public interface RoleDAO extends BaseJpaDAO<HsptRole>,QuerydslBinderCustomizer<QHsptRole> {

    /**
     * 增加对查询条件的模糊搜索支持
     * @param bindings
     * @param role
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptRole role) {
        bindings.bind(role.roleCode).first(StringExpression::containsIgnoreCase);
        bindings.bind(role.roleName).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 查询系统角色，使用角色编码作为KEY
     *
     * @param roleCode
     * @return
     */
    @Cacheable(key = "#p0+#p1")
    HsptRole findByRoleCodeAndDr(String roleCode, int dr);

    @Cacheable(key = "#p0+#p1")
    HsptRole findByRoleNameAndDr(String roleName, int dr);

    @Cacheable(key = "#p0+#p1")
    HsptRole findByPkRoleAndDr(long pkRole, int dr);

    @Cacheable(key = "#p0")
    List<HsptRole> findByDr(int dr);

    @Cacheable(key = "#p0+#p1+#p2")
    HsptRole findByRoleCodeAndPkGroupAndDr(String roleCode, long pkGroup, int dr);


}
