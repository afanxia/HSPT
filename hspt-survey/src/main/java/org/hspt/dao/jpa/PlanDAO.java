package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptPlan;
import org.hspt.entity.jpa.QHsptPlan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 计划信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "plan")
public interface PlanDAO extends BaseJpaDAO<HsptPlan>, QuerydslBinderCustomizer<QHsptPlan> {


    /**
     * 根据计划主键查找
     *
     * @param planId
     * @return
     */
    HsptPlan findByPlanId(Integer planId);
}
