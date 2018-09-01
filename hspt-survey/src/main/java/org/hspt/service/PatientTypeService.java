package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqPatientType;
import org.springframework.data.domain.Pageable;

/**
 * <b> 病人分类管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface PatientTypeService {

    /**
     * 添加病人分类
     *
     * @param patientType
     * @return
     */
    BaseResponse addPatientType(ReqPatientType patientType) throws BaseException;

    /**
     * 修改病人分类
     *
     * @param patientType
     * @return
     */
    BaseResponse updatePatientType(ReqPatientType patientType) throws BaseException;

    /**
     * 删除病人分类
     *
     * @param patientTypeId
     * @return
     */
    BaseResponse delPatientType(Integer patientTypeId) throws BaseException;


    /**
     * 获取病人分类列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatientTypeAll(Predicate predicate) throws BaseException;

    /**
     * 获取病人分类列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatientTypes(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取病人分类总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatientTypeCount(Predicate predicate) throws BaseException;


}
