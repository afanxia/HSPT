package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptPlan;
import org.hspt.entity.request.ReqPlan;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPlan;
import org.hspt.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 计划中心公有接口 </b>
 * <p>
 * 功能描述:提供一些公共接口
 * </p>
 */
@RequestMapping("/plan")
@RestController
@Api(tags = "计划中心接口", description = "计划中心接口")
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
public class PlanController {

    @Autowired
    private PlanService planService;


    @ApiOperation(value = "获取全部计划", notes = "获取全部计划,主要用于下拉使用")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResPlan>> getPlanAll(@QuerydslPredicate(root = HsptPlan.class) Predicate predicate) throws BaseException {
        return planService.getPlanAll(predicate);
    }

    @ApiOperation(value = "获取计划数量", notes = "获取计划，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptPlan.class) Predicate plan) throws BaseException {
        return planService.getPlanCount(plan);
    }
    @ApiOperation(value = "添加计划", notes = "添加计划，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResPlan> addPlan(@RequestBody @Validated ReqPlan plan) throws BaseException {
        return planService.addPlan(plan);
    }


    @ApiOperation(value = "删除计划", notes = "删除计划，系统管理员默认可以访问")
    @RequestMapping(value = "/{planId}", method = RequestMethod.DELETE)
    public BaseResponse delPlan(@PathVariable("planId") Integer planId) throws BaseException {
        return planService.delPlan(planId);
    }

    @ApiOperation(value = "修改计划", notes = "修改计划，系统管理员默认可以访问")
    @RequestMapping(value = "/{planId}", method = RequestMethod.PUT)
    public BaseResponse updatePlan(@PathVariable("planId") Integer planId, @RequestBody @Validated ReqPlan plan) throws BaseException {
        return planService.updatePlan(plan);
    }

    @ApiOperation(value = "获取全部计划，分页查询", notes = "获取全部计划，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<List<ResPlan>> getPlans(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                @QuerydslPredicate(root = HsptPlan.class) Predicate predicate) throws BaseException {
        Pageable pageable = new PageRequest(page, size);
        return planService.getPlans(pageable, predicate);
    }
}
