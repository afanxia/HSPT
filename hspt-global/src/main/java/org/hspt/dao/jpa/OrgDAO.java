package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptOrg;

/**
 * <b> 机构信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
public interface OrgDAO extends BaseJpaDAO<HsptOrg> {
    /**
     * 根据编码查询机构信息
     *
     * @param orgCode 机构代码
     * @param dr      删除标志
     * @return
     */
    HsptOrg findByOrgCodeAndDr(String orgCode, int dr);
}

