package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptDeliveryInfo;
import org.hspt.entity.request.ReqDeliveryInfo;
import org.springframework.data.domain.Pageable;

/**
 * <b> 问卷分发管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface DeliveryInfoService {

    /**
     * 添加问卷分发
     *
     * @param deliveryInfo
     * @return
     */
    BaseResponse addDeliveryInfo(ReqDeliveryInfo deliveryInfo) throws BaseException;

    /**
     * 修改问卷分发
     *
     * @param deliveryInfo
     * @return
     */
    BaseResponse updateDeliveryInfo(HsptDeliveryInfo deliveryInfo) throws BaseException;

    /**
     * 删除问卷分发
     *
     * @param deliveryInfoId
     * @return
     */
    BaseResponse delDeliveryInfo(Integer deliveryInfoId) throws BaseException;


    /**
     * 获取问卷分发列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getDeliveryInfoAll(Predicate predicate) throws BaseException;

    /**
     * 获取问卷分发列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getDeliveryInfos(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取问卷分发总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getDeliveryInfoCount(Predicate predicate) throws BaseException;


}
