package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.entity.request.ReqSurvey;
import org.hspt.entity.response.ResCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptSurvey;
import org.hspt.entity.response.ResSurvey;
import org.hspt.service.SurveyService;

import java.util.List;

/**
 * <b> 问卷中心公有接口 </b>
 * <p>
 * 功能描述:提供一些公共接口
 * </p>
 */
@RequestMapping("/survey")
@RestController
@Api(tags = "问卷中心接口", description = "问卷中心接口")
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
public class SurveyController {

    @Autowired
    private SurveyService surveyService;


    @ApiOperation(value = "获取全部问卷", notes = "获取全部问卷,主要用于下拉使用")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResSurvey>> getSurveyAll(@QuerydslPredicate(root = HsptSurvey.class) Predicate predicate) throws BaseException {
        return surveyService.getSurveyAll(predicate);
    }

    @ApiOperation(value = "获取问卷数量", notes = "获取问卷，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptSurvey.class) Predicate survey) throws BaseException {
        return surveyService.getSurveyCount(survey);
    }
    @ApiOperation(value = "添加问卷", notes = "添加问卷，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResSurvey> addSurvey(@RequestBody @Validated ReqSurvey survey) throws BaseException {
        return surveyService.addSurvey(survey);
    }


    @ApiOperation(value = "删除问卷", notes = "删除问卷，系统管理员默认可以访问")
    @RequestMapping(value = "/{surveyId}", method = RequestMethod.DELETE)
    public BaseResponse delSurvey(@PathVariable("surveyId") long surveyId) throws BaseException {
        return surveyService.delSurvey(surveyId);
    }

    @ApiOperation(value = "修改问卷", notes = "修改问卷，系统管理员默认可以访问")
    @RequestMapping(value = "/{surveyId}", method = RequestMethod.PUT)
    public BaseResponse updateSurvey(@PathVariable("surveyId") long surveyId, @RequestBody @Validated ReqSurvey survey) throws BaseException {
        return surveyService.updateSurvey(survey);
    }

    @ApiOperation(value = "获取全部问卷，分页查询", notes = "获取全部问卷，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<List<ResSurvey>> getSurveys(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                @QuerydslPredicate(root = HsptSurvey.class) Predicate predicate) throws BaseException {
        Pageable pageable = new PageRequest(page, size);
        return surveyService.getSurveys(pageable, predicate);
    }
}
