package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.entity.jpa.HsptSurvey;
import org.hspt.entity.jpa.QHsptSurvey;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;

/**
 * <b> 问卷信息 </b>
 * <p>
 * 功能描述:问卷基础信息表操作
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "survey")
public interface SurveyDAO extends BaseJpaDAO<HsptSurvey>, QuerydslBinderCustomizer<QHsptSurvey> {
    /**
     * 根据问卷主键查找
     *
     * @param pkSurvey
     * @return
     */
    HsptSurvey findByPkSurvey(long pkSurvey);

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param survey
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptSurvey survey) {
        bindings.bind(survey.surveyName).first(StringExpression::containsIgnoreCase);
    }

}
