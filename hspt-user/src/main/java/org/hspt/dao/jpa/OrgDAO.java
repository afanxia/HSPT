package org.hspt.dao.jpa;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.QHsptOrg;
import org.hspt.entity.jpa.HsptOrg;

/**
 * <b> 机构信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 * @author zhangshuai
 * @date 2017/11/14
 * @time 10:41
 * @Path org.hspt.dao.jpa.ImpOrgDAO
 */
@Repository
@CacheConfig(cacheNames = "orgs")
public interface OrgDAO extends BaseJpaDAO<HsptOrg>, QuerydslBinderCustomizer<QHsptOrg> {

    /**
     * 增加对查询条件的模糊搜索支持
     *
     * @param bindings
     * @param org
     */
    @Override
    default void customize(QuerydslBindings bindings, QHsptOrg org) {
        bindings.bind(org.orgName).first(StringExpression::containsIgnoreCase);
    }

    /**
     * 根据编码查询机构信息
     *
     * @param orgCode 机构代码
     * @param dr      删除标志
     * @return
     */
    HsptOrg findByOrgCodeAndDr(String orgCode, int dr);

    /**
     * 根据编码和组织查询机构信息
     *
     * @param pkGroup 组织主键
     * @param orgCode 机构代码
     * @param dr      删除标志
     * @return
     */
    HsptOrg findByPkGroupAndOrgCodeAndDr(long pkGroup, String orgCode, int dr);

    /**
     * 根据主键查询机构信息
     *
     * @param pkOrg 机构主键
     * @param dr    删除标志
     * @return
     */
    HsptOrg findByPkOrgAndDr(long pkOrg, int dr);
}
