package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptAnswer;
import org.hspt.entity.jpa.QHsptAnswer;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 答案信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "answer")
public interface AnswerDAO extends BaseJpaDAO<HsptAnswer>, QuerydslBinderCustomizer<QHsptAnswer> {

    /**
     * 根据答案主键查找
     *
     * @param answerId
     * @return
     */
    HsptAnswer findByAnswerId(Integer answerId);
}
