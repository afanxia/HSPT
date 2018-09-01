package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqPatient;
import org.springframework.data.domain.Pageable;

/**
 * <b> 病人管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface PatientService {

    /**
     * 添加病人
     *
     * @param patient
     * @return
     */
    BaseResponse addPatient(ReqPatient patient) throws BaseException;

    /**
     * 修改病人
     *
     * @param patient
     * @return
     */
    BaseResponse updatePatient(ReqPatient patient) throws BaseException;

    /**
     * 删除病人
     *
     * @param patientId
     * @return
     */
    BaseResponse delPatient(Integer patientId) throws BaseException;


    /**
     * 获取病人列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatientAll(Predicate predicate) throws BaseException;

    /**
     * 获取病人列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatients(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取病人总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getPatientCount(Predicate predicate) throws BaseException;


}
