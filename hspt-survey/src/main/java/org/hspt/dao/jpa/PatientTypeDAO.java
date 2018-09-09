package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptPatientType;
import org.hspt.entity.jpa.QHsptPatient;
import org.hspt.entity.jpa.QHsptPatientType;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 病人类型信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "patientType")
public interface PatientTypeDAO extends BaseJpaDAO<HsptPatientType>, QuerydslBinderCustomizer<QHsptPatientType> {

    /**
     * 根据病人类型主键查找
     *
     * @param patientTypeId
     * @return
     */
    HsptPatientType findByPatientTypeId(Integer patientTypeId);

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param patientType
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptPatientType patientType) {
        bindings.bind(patientType.patientTypeName).first(StringExpression::containsIgnoreCase);
    }
}
