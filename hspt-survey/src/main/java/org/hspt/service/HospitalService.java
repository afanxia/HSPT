package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqHospital;

/**
 * <b> 提供医院的维护管理 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface HospitalService {

    /**
     * 添加医院
     *
     * @param hospital
     * @return
     */
    BaseResponse addHospital(ReqHospital hospital) throws BaseException;

    /**
     * 修改医院
     *
     * @param hospital
     * @return
     */
    BaseResponse updateHospital(ReqHospital hospital) throws BaseException;

    /**
     * 删除医院
     *
     * @param hospitalId
     * @return
     */
    BaseResponse delHospital(Integer hospitalId) throws BaseException;


    /**
     * 获取医院列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getHospitalAll(Predicate predicate) throws BaseException;


    /**
     * 获取医院总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getHospitalCount(Predicate predicate) throws BaseException;


}
