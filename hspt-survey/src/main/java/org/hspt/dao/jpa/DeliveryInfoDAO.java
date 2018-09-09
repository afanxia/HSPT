package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptDeliveryInfo;
import org.hspt.entity.jpa.QHsptDeliveryInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 问卷分发信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "deliveryInfo")
public interface DeliveryInfoDAO extends BaseJpaDAO<HsptDeliveryInfo>, QuerydslBinderCustomizer<QHsptDeliveryInfo> {

    /**
     * 根据问卷分发主键查找
     *
     * @param deliveryId
     * @return
     */
    HsptDeliveryInfo findByDeliveryId(Integer deliveryId);

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param deliveryInfo
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptDeliveryInfo deliveryInfo) {
        bindings.bind(deliveryInfo.survey.surveyName).first(StringExpression::containsIgnoreCase);
    }
}
