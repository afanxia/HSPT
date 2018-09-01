package org.hspt.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptDeliveryInfo;
import org.hspt.entity.request.ReqDeliveryInfo;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResDeliveryInfo;
import org.hspt.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 问卷分发中心公有接口 </b>
 * <p>
 * 功能描述:提供一些公共接口
 * </p>
 */
@RequestMapping("/deliveryInfo")
@RestController
@Api(tags = "问卷分发中心接口", description = "问卷分发中心接口")
@AuthRoles(roles = {BaseConstants.ALL_USER_NAME})
public class DeliveryInfoController {

    @Autowired
    private DeliveryInfoService deliveryInfoService;


    @ApiOperation(value = "获取全部问卷分发", notes = "获取全部问卷分发,主要用于下拉使用")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List<ResDeliveryInfo>> getDeliveryInfoAll(@QuerydslPredicate(root = HsptDeliveryInfo.class) Predicate predicate) throws BaseException {
        return deliveryInfoService.getDeliveryInfoAll(predicate);
    }

    @ApiOperation(value = "获取问卷分发数量", notes = "获取问卷分发，系统管理员默认可以访问")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponse<ResCount> getCount(@QuerydslPredicate(root = HsptDeliveryInfo.class) Predicate deliveryInfo) throws BaseException {
        return deliveryInfoService.getDeliveryInfoCount(deliveryInfo);
    }
    @ApiOperation(value = "添加问卷分发", notes = "添加问卷分发，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse<ResDeliveryInfo> addDeliveryInfo(@RequestBody @Validated ReqDeliveryInfo deliveryInfo) throws BaseException {
        return deliveryInfoService.addDeliveryInfo(deliveryInfo);
    }


    @ApiOperation(value = "删除问卷分发", notes = "删除问卷分发，系统管理员默认可以访问")
    @RequestMapping(value = "/{deliveryInfoId}", method = RequestMethod.DELETE)
    public BaseResponse delDeliveryInfo(@PathVariable("deliveryInfoId") Integer deliveryInfoId) throws BaseException {
        return deliveryInfoService.delDeliveryInfo(deliveryInfoId);
    }

    //@ApiOperation(value = "修改问卷分发", notes = "修改问卷分发，系统管理员默认可以访问")
    //@RequestMapping(value = "/{deliveryInfoId}", method = RequestMethod.PUT)
    //public BaseResponse updateDeliveryInfo(@PathVariable("deliveryInfoId") Long deliveryInfoId, @RequestBody @Validated ReqDeliveryInfo deliveryInfo) throws BaseException {
    //    return deliveryInfoService.updateDeliveryInfo(deliveryInfo);
    //}

    @ApiOperation(value = "获取全部问卷分发，分页查询", notes = "获取全部问卷分发，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<List<ResDeliveryInfo>> getDeliveryInfos(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                @QuerydslPredicate(root = HsptDeliveryInfo.class) Predicate predicate) throws BaseException {
        Pageable pageable = new PageRequest(page, size);
        return deliveryInfoService.getDeliveryInfos(pageable, predicate);
    }
}
