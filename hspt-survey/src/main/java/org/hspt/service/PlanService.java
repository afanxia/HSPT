package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqPlan;
import org.springframework.data.domain.Pageable;

/**
 * <b> 计划管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface PlanService {

    /**
     * 添加计划
     *
     * @param plan
     * @return
     */
    BaseResponse addPlan(ReqPlan plan) throws BaseException;

    /**
     * 修改计划
     *
     * @param plan
     * @return
     */
    BaseResponse updatePlan(ReqPlan plan) throws BaseException;

    /**
     * 删除计划
     *
     * @param planId
     * @return
     */
    BaseResponse delPlan(Integer planId) throws BaseException;


    /**
     * 获取计划列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPlanAll(Predicate predicate) throws BaseException;

    /**
     * 获取计划列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPlans(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取计划总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPlanCount(Predicate predicate) throws BaseException;


}
