package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptPatient;
import org.hspt.entity.request.ReqPatient;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPatient;
import org.hspt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 病人中心公有接口 </b>
 * <p>
 * 功能描述:提供一些公共接口
 * </p>
 */
@RequestMapping("/patient")
@RestController
@Api(tags = "病人中心接口", description = "病人中心接口")
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
public class PatientController {

    @Autowired
    private PatientService patientService;


    @ApiOperation(value = "获取全部病人", notes = "获取全部病人,主要用于下拉使用")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResPatient>> getPatientAll(@QuerydslPredicate(root = HsptPatient.class) Predicate predicate) throws BaseException {
        return patientService.getPatientAll(predicate);
    }

    @ApiOperation(value = "获取病人数量", notes = "获取病人，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptPatient.class) Predicate patient) throws BaseException {
        return patientService.getPatientCount(patient);
    }
    @ApiOperation(value = "添加病人", notes = "添加病人，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResPatient> addPatient(@RequestBody @Validated ReqPatient patient) throws BaseException {
        return patientService.addPatient(patient);
    }


    @ApiOperation(value = "删除病人", notes = "删除病人，系统管理员默认可以访问")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.DELETE)
    public BaseResponse delPatient(@PathVariable("patientId") Integer patientId) throws BaseException {
        return patientService.delPatient(patientId);
    }

    @ApiOperation(value = "修改病人", notes = "修改病人，系统管理员默认可以访问")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.PUT)
    public BaseResponse updatePatient(@PathVariable("patientId") Integer patientId, @RequestBody @Validated ReqPatient patient) throws BaseException {
        return patientService.updatePatient(patient);
    }

    @ApiOperation(value = "获取全部病人，分页查询", notes = "获取全部病人，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<List<ResPatient>> getPatients(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                @QuerydslPredicate(root = HsptPatient.class) Predicate predicate) throws BaseException {
        Pageable pageable = new PageRequest(page, size);
        return patientService.getPatients(pageable, predicate);
    }
}
