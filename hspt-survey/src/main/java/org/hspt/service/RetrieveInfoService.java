package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.jpa.HsptRetrieveInfo;
import org.hspt.entity.request.ReqRetrieveInfo;
import org.springframework.data.domain.Pageable;

/**
 * <b> 问卷回收管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface RetrieveInfoService {

    /**
     * 添加问卷回收
     *
     * @param retrieveInfo
     * @return
     */
    BaseResponse addRetrieveInfo(ReqRetrieveInfo retrieveInfo) throws BaseException;

    /**
     * 修改问卷回收
     *
     * @param retrieveInfo
     * @return
     */
    BaseResponse updateRetrieveInfo(HsptRetrieveInfo retrieveInfo) throws BaseException;

    /**
     * 删除问卷回收
     *
     * @param retrieveInfoId
     * @return
     */
    BaseResponse delRetrieveInfo(Integer retrieveInfoId) throws BaseException;


    /**
     * 获取问卷回收列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getRetrieveInfoAll(Predicate predicate) throws BaseException;

    /**
     * 获取问卷回收列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getRetrieveInfos(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取问卷回收总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getRetrieveInfoCount(Predicate predicate) throws BaseException;


}
