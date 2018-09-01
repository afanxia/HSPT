package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptPatientType;
import org.hspt.entity.request.ReqPatientType;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPatientType;
import org.hspt.service.PatientTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 病人类型接口  </b>
 * <p>
 * 功能描述:提供病人类型管理的接口
 * </p>
 */
@RequestMapping("/patientType")
@RestController
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
@Api(tags = "病人类型接口", description = "提供病人类型相关接口管理")
public class PatientTypeController {


    @Autowired
    private PatientTypeService patientTypeService;


    @ApiOperation(value = "添加病人类型", notes = "添加病人类型，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResPatientType> addSurvey(@RequestBody @Validated ReqPatientType patientType) throws BaseException {
        return patientTypeService.addPatientType(patientType);
    }

    @ApiOperation(value = "删除病人类型", notes = "删除病人类型,系统管理员默认可以访问")
    @RequestMapping(value = "/{patientTypeId}", method = RequestMethod.DELETE)
    public BaseResponse delSysPatientType(@PathVariable("patientTypeId") Integer patientTypeId) throws BaseException {
        return patientTypeService.delPatientType(patientTypeId);
    }

    @ApiOperation(value = "修改病人类型", notes = "修改病人类型，系统管理员默认可以访问")
    @RequestMapping(value = "/{patientTypeId}", method = RequestMethod.PUT)
    public BaseResponse setSysPatientType(@PathVariable("patientTypeId") Integer patientTypeId, @RequestBody @Validated ReqPatientType patientType) throws BaseException {
        return patientTypeService.updatePatientType(patientType);
    }

    @ApiOperation(value = "获取全部病人类型", notes = "获取全部病人类型多用于下拉，系统管理员默认可以访问")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResPatientType>> getAll(@QuerydslPredicate(root = HsptPatientType.class) Predicate predicate) throws BaseException {
        return patientTypeService.getPatientTypeAll(predicate);
    }

    @ApiOperation(value = "获取病人类型数量", notes = "获取病人类型数量，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptPatientType.class) Predicate patientType) throws BaseException {
        return patientTypeService.getPatientTypeCount(patientType);
    }
}