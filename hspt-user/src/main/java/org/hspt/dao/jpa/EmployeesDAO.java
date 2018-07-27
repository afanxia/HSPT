package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.QHsptEmployees;
import org.hspt.entity.jpa.HsptEmployees;

/**
 * <b> 雇员信息 </b>
 * <p>
 * 功能描述:雇员基础信息表操作
 * </p>
 */
@Repository
@CacheConfig(cacheNames = "employees")
public interface EmployeesDAO extends BaseJpaDAO<HsptEmployees>, QuerydslBinderCustomizer<QHsptEmployees> {
    /**
     * 根据员工主键和删除状态查找
     *
     * @param pkEmployees
     * @param dr
     * @return
     */
    HsptEmployees findByPkEmployeesAndDr(long pkEmployees, int dr);

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param emp
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptEmployees emp) {
        bindings.bind(emp.employeesCode).first(StringExpression::containsIgnoreCase);
        bindings.bind(emp.employeesName).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 按员工编号和删除标识查找
     *
     * @param employeesCode 员工编号
     * @param dr            删除标识
     * @return
     */
    HsptEmployees findByEmployeesCodeAndDr(String employeesCode, int dr);
}
