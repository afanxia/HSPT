package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptChoice;
import org.hspt.entity.jpa.QHsptChoice;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 选项信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "choice")
public interface ChoiceDAO extends BaseJpaDAO<HsptChoice>, QuerydslBinderCustomizer<QHsptChoice> {

    /**
     * 根据选项主键查找
     *
     * @param choiceId
     * @return
     */
    HsptChoice findByChoiceId(Integer choiceId);
}
