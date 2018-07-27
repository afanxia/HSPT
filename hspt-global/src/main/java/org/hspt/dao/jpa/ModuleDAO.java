package org.hspt.dao.jpa;

import org.springframework.stereotype.Repository;
import org.hspt.base.BaseJpaDAO;
import org.hspt.entity.jpa.HsptModule;

/**
 * <b> 模块信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Repository
public interface ModuleDAO extends BaseJpaDAO<HsptModule> {

    /**
     * 获取模块信息
     *
     * @param moduleId 模块编码
     * @param dr       删除标识
     * @return
     */
    HsptModule findByModuleIdAndDr(String moduleId, int dr);

}

