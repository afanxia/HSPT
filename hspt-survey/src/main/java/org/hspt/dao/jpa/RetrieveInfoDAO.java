package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptRetrieveInfo;
import org.hspt.entity.jpa.QHsptRetrieveInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 问卷回收信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "retrieveInfo")
public interface RetrieveInfoDAO extends BaseJpaDAO<HsptRetrieveInfo>, QuerydslBinderCustomizer<QHsptRetrieveInfo> {

    /**
     * 根据问卷回收主键查找
     *
     * @param deliveryId
     * @return
     */
    HsptRetrieveInfo findByDeliveryId(Integer deliveryId);
}
