package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptPermissionsResource;

import java.util.List;

/**
 * <b> 授权接口信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
public interface PermissionsResourceDAO extends BaseJpaDAO<HsptPermissionsResource> {

    /**
     * 根据权限组主键、资源主键、删除标识获取资源权限信息
     *
     * @param pkPermissions 资源权限信息
     * @param pkResource    资源信息
     * @param dr            删除标识
     * @return
     */
    HsptPermissionsResource findByPkPermissionsAndPkResourceAndDr(long pkPermissions, long pkResource, int dr);

    /**
     * 根据权限资源ID获取资源详情信息
     *
     * @param pkPermissions 资源权限ID
     * @param dr            删除标识
     * @return
     */
    List<HsptPermissionsResource> findByPkPermissionsAndDr(long pkPermissions, int dr);
}
