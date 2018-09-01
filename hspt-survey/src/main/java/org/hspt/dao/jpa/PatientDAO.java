package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptPatient;
import org.hspt.entity.jpa.QHsptPatient;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * <b> 病人信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "patient")
public interface PatientDAO extends BaseJpaDAO<HsptPatient>, QuerydslBinderCustomizer<QHsptPatient> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param patient
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptPatient patient) {
        bindings.bind(patient.name).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 根据病人主键查找
     *
     * @param patientId
     * @return
     */
    HsptPatient findByPatientId(Integer patientId);

    /**
     * 根据病人姓名查找
     *
     * @param name
     * @return
     */
    HsptPatient findByName(String name);
}
