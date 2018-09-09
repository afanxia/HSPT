package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptQuestion;
import org.hspt.entity.jpa.QHsptQuestion;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 问题信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "question")
public interface QuestionDAO extends BaseJpaDAO<HsptQuestion>, QuerydslBinderCustomizer<QHsptQuestion> {


    /**
     * 根据问题主键查找
     *
     * @param questionId
     * @return
     */
    HsptQuestion findByQuestionId(Integer questionId);

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param question
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptQuestion question) {
        bindings.bind(question.questionContent).first(StringExpression::containsIgnoreCase);
    }
}
