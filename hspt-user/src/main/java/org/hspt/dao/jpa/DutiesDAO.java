package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.QHsptDuties;
import org.hspt.entity.jpa.HsptDuties;

/**
 * <b> 职务信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "duties")
public interface DutiesDAO extends BaseJpaDAO<HsptDuties>, QuerydslBinderCustomizer<QHsptDuties> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param duties
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptDuties duties) {
        bindings.bind(duties.dutiesCode).first(StringExpression::containsIgnoreCase);
        bindings.bind(duties.dutiesName).first(StringExpression::containsIgnoreCase);
    }
}
