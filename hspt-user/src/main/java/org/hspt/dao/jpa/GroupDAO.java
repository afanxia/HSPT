package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.QHsptGroup;
import org.hspt.entity.jpa.HsptGroup;

/**
 * <b> 组织信息DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Repository
@CacheConfig(cacheNames = "groups")
public interface GroupDAO extends BaseJpaDAO<HsptGroup>, QuerydslBinderCustomizer<QHsptGroup> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param group
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptGroup group) {
        bindings.bind(group.groupCode).first(StringExpression::containsIgnoreCase);
        bindings.bind(group.groupName).first(StringExpression::containsIgnoreCase);
    }

    @Cacheable(key = "#p0+#p1")
    HsptGroup findByGroupCodeAndDr(String groupCode, int dr);

    /**
     * 根据主键和删除标识查找
     *
     * @param pkGroup
     * @param dr
     * @return
     */
    HsptGroup findByPkGroupAndDr(Long pkGroup, int dr);


}
