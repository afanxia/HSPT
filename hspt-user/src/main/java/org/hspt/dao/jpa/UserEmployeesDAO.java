package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptUserEmployees;

/**
 * <b> HsptUserEmployeesDAO </b>
 * <p>
 * 功能描述:用户员工关系服务
 * </p>
 */
@Repository
public interface UserEmployeesDAO extends BaseJpaDAO<HsptUserEmployees> {
    /**
     * 根据关系主键和删除状态查找
     *
     * @param pkUserEmployees
     * @param dr
     * @return
     */
    HsptUserEmployees findByPkUserEmployeesAndDr(long pkUserEmployees, int dr);

    /**
     * 根据员工主键和删除状态查找
     *
     * @param pkEmployees
     * @param dr
     * @return
     */
    HsptUserEmployees findByPkEmployeesAndDr(long pkEmployees, int dr);

    /**
     * 根据用户主键和删除状态查找
     *
     * @param pkUser
     * @param dr
     * @return
     */
    HsptUserEmployees findByPkUserAndDr(long pkUser, int dr);
}
