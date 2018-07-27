package org.hspt.dao.jpa;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptEmployeesDuties;

/**
 * <b> 人员职务DAO类 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Repository
@CacheConfig(cacheNames = "employees")
public interface EmployeesDutiesDAO extends BaseJpaDAO<HsptEmployeesDuties> {
    /**
     * 按员工主键和职务主键查找
     *
     * @param pkEmployees 员工主键
     * @param pkDuties    职务主键
     * @return
     */
    HsptEmployeesDuties findByPkEmployeesAndPkDutiesAndDr(long pkEmployees, long pkDuties, int dr);
}
