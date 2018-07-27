package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptAuthorization;

/**
 * <b> 第三方授权信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
public interface AuthorizationDAO extends BaseJpaDAO<HsptAuthorization> {


    /**
     * 根据accessKey与secretKey查询授权信息
     *
     * @param accessKey
     * @param secretKey
     * @return
     */
    HsptAuthorization findByAccessKeyAndSecretKey(String accessKey, String secretKey);


}
