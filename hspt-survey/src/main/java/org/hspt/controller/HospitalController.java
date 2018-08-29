package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptHospital;
import org.hspt.entity.request.ReqHospital;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResHospital;
import org.hspt.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 医院接口  </b>
 * <p>
 * 功能描述:提供医院管理的接口
 * </p>
 */
@RequestMapping("/hospital")
@RestController
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
@Api(tags = "医院接口", description = "提供医院相关接口管理")
public class HospitalController {


    @Autowired
    private HospitalService hospitalService;


    @ApiOperation(value = "添加医院", notes = "添加医院，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResHospital> addSurvey(@RequestBody @Validated ReqHospital hospital) throws BaseException {
        return hospitalService.addHospital(hospital);
    }

    @ApiOperation(value = "删除医院", notes = "删除医院,系统管理员默认可以访问")
    @RequestMapping(value = "/{hospitalId}", method = RequestMethod.DELETE)
    public BaseResponse delSysHospital(@PathVariable("hospitalId") long hospitalId) throws BaseException {
        return hospitalService.delHospital(hospitalId);
    }

    @ApiOperation(value = "修改医院", notes = "修改医院，系统管理员默认可以访问")
    @RequestMapping(value = "/{hospitalId}", method = RequestMethod.PUT)
    public BaseResponse setSysHospital(@PathVariable("hospitalId") long hospitalId, @RequestBody @Validated ReqHospital hospital) throws BaseException {
        return hospitalService.updateHospital(hospital);
    }

    @ApiOperation(value = "获取全部医院", notes = "获取全部医院多用于下拉，系统管理员默认可以访问")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResHospital>> getAll(@QuerydslPredicate(root = HsptHospital.class) Predicate predicate) throws BaseException {
        return hospitalService.getHospitalAll(predicate);
    }

    @ApiOperation(value = "获取医院数量", notes = "获取医院数量，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptHospital.class) Predicate hospital) throws BaseException {
        return hospitalService.getHospitalCount(hospital);
    }
}