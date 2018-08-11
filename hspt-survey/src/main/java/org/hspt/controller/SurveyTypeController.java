package org.hspt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.entity.jpa.HsptSurveyType;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResSurveyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hspt.base.*;
import com.querydsl.core.types.Predicate;
import org.hspt.entity.request.*;
import org.hspt.service.SurveyTypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <b> 问卷类型接口  </b>
 * <p>
 * 功能描述:提供问卷类型的接口
 * </p>
 */
@RequestMapping("/surveyType")
@RestController
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
@Api(tags = "问卷类型接口", description = "提供问卷类型的相关接口管理")
public class SurveyTypeController {


    @Autowired
    private SurveyTypeService surveyTypeService;


    @ApiOperation(value = "添加问卷类型", notes = "添加问卷类型，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResSurveyType> addSurvey(@RequestBody @Validated ReqSurveyType surveyType) throws BaseException {
        return surveyTypeService.addSurveyType(surveyType);
    }

    @ApiOperation(value = "删除问卷类型", notes = "删除问卷类型统管理员默认可以访问")
    @RequestMapping(value = "/{surveyTypeId}", method = RequestMethod.DELETE)
    public BaseResponse delSysSurveyType(@PathVariable("surveyTypeId") Integer surveyTypeId) throws BaseException {
        return surveyTypeService.delSurveyType(surveyTypeId);
    }

    @ApiOperation(value = "修改问卷类型", notes = "修改问卷类型，系统管理员默认可以访问")
    @RequestMapping(value = "/{surveyTypeId}", method = RequestMethod.PUT)
    public BaseResponse setSysSurveyType(@PathVariable("surveyTypeId") Integer surveyTypeId, @RequestBody @Validated ReqSurveyType surveyType) throws BaseException {
        return surveyTypeService.updateSurveyType(surveyType);
    }

    @ApiOperation(value = "获取全部问卷类型", notes = "获取全部问卷类型多用于下拉，系统管理员默认可以访问")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResSurveyType>> getAll(@QuerydslPredicate(root = HsptSurveyType.class) Predicate predicate) throws BaseException {
        return surveyTypeService.getSurveyTypeAll(predicate);
    }

    @ApiOperation(value = "获取问卷类型数量", notes = "获取问卷类型数量，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptSurveyType.class) Predicate surveyType) throws BaseException {
        return surveyTypeService.getSurveyTypeCount(surveyType);
    }
}