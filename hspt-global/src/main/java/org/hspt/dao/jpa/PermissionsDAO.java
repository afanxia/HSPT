package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptPermissions;

import java.util.List;

/**
 * <b> 授权信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
public interface PermissionsDAO extends BaseJpaDAO<HsptPermissions> {

    /**
     * 根据编码、名称、删除标识获取权限资源信息
     *
     * @param code 编码
     * @param name 名称
     * @param dr   删除标识
     * @return
     */
    HsptPermissions findByPermissionsCodeAndPermissionsNameAndDr(String code, String name, int dr);


    /**
     * 根据删除标识获取权限资源信息
     *
     * @param dr   删除标识
     * @return
     */
    List<HsptPermissions> findByDr(int dr);


    /**
     * 根据编码、删除标识获取权限资源信息
     *
     * @param code 编码
     * @param dr   删除标识
     * @return
     */
    HsptPermissions findByPermissionsCodeAndDr(String code, int dr);
}
