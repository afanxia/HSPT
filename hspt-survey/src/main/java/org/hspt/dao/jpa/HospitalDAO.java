package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptHospital;
import org.hspt.entity.jpa.QHsptHospital;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 医院信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "hospital")
public interface HospitalDAO extends BaseJpaDAO<HsptHospital>, QuerydslBinderCustomizer<QHsptHospital> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param hospital
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptHospital hospital) {
        bindings.bind(hospital.name).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 根据医院主键查找
     *
     * @param hospitalId
     * @return
     */
    HsptHospital findByHospitalId(long hospitalId);
}
