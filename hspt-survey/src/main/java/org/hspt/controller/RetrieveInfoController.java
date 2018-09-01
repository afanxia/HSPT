package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptRetrieveInfo;
import org.hspt.entity.request.ReqRetrieveInfo;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResRetrieveInfo;
import org.hspt.service.RetrieveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 问卷回收中心公有接口 </b>
 * <p>
 * 功能描述:提供一些公共接口
 * </p>
 */
@RequestMapping("/retrieveInfo")
@RestController
@Api(tags = "问卷回收中心接口", description = "问卷回收中心接口")
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
public class RetrieveInfoController {

    @Autowired
    private RetrieveInfoService retrieveInfoService;


    @ApiOperation(value = "获取全部问卷回收", notes = "获取全部问卷回收,主要用于下拉使用")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResRetrieveInfo>> getRetrieveInfoAll(@QuerydslPredicate(root = HsptRetrieveInfo.class) Predicate predicate) throws BaseException {
        return retrieveInfoService.getRetrieveInfoAll(predicate);
    }

    @ApiOperation(value = "获取问卷回收数量", notes = "获取问卷回收，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptRetrieveInfo.class) Predicate retrieveInfo) throws BaseException {
        return retrieveInfoService.getRetrieveInfoCount(retrieveInfo);
    }
    @ApiOperation(value = "添加问卷回收", notes = "添加问卷回收，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResRetrieveInfo> addRetrieveInfo(@RequestBody @Validated ReqRetrieveInfo retrieveInfo) throws BaseException {
        return retrieveInfoService.addRetrieveInfo(retrieveInfo);
    }


    @ApiOperation(value = "删除问卷回收", notes = "删除问卷回收，系统管理员默认可以访问")
    @RequestMapping(value = "/{retrieveInfoId}", method = RequestMethod.DELETE)
    public BaseResponse delRetrieveInfo(@PathVariable("retrieveInfoId") Integer retrieveInfoId) throws BaseException {
        return retrieveInfoService.delRetrieveInfo(retrieveInfoId);
    }

    //@ApiOperation(value = "修改问卷回收", notes = "修改问卷回收，系统管理员默认可以访问")
    //@RequestMapping(value = "/{retrieveInfoId}", method = RequestMethod.PUT)
    //public BaseResponse updateRetrieveInfo(@PathVariable("retrieveInfoId") Long retrieveInfoId, @RequestBody @Validated ReqRetrieveInfo retrieveInfo) throws BaseException {
    //    return retrieveInfoService.updateRetrieveInfo(retrieveInfo);
    //}

    @ApiOperation(value = "获取全部问卷回收，分页查询", notes = "获取全部问卷回收，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<List<ResRetrieveInfo>> getRetrieveInfos(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                @QuerydslPredicate(root = HsptRetrieveInfo.class) Predicate predicate) throws BaseException {
        Pageable pageable = new PageRequest(page, size);
        return retrieveInfoService.getRetrieveInfos(pageable, predicate);
    }
}
