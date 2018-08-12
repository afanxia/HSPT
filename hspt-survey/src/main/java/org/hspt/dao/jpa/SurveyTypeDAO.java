package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.entity.jpa.HsptSurveyType;
import org.hspt.entity.jpa.QHsptSurveyType;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;

/**
 * <b> 问卷类型信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "duties")
public interface SurveyTypeDAO extends BaseJpaDAO<HsptSurveyType>, QuerydslBinderCustomizer<QHsptSurveyType> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param surveyType
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptSurveyType surveyType) {
        bindings.bind(surveyType.surveyTypeName).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 根据问卷类型主键查找
     *
     * @param pkSurveyType
     * @return
     */
    HsptSurveyType findByPkSurveyType(long pkSurveyType);
}
