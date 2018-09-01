package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.PatientDAO;
import org.hspt.dao.jpa.PatientTypeDAO;
import org.hspt.entity.jpa.HsptPatient;
import org.hspt.entity.jpa.HsptPatientType;
import org.hspt.entity.jpa.QHsptPatient;
import org.hspt.entity.request.ReqPatient;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPatient;
import org.hspt.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b> 提供病人的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class PatientServiceImpl extends BaseService implements PatientService {

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private PatientTypeDAO patientTypeDAO;


    @Override
    public BaseResponse addPatient(ReqPatient patient) {
        if (null != patientDAO.findByPatientId(patient.getPatientId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptPatient impPatient = new HsptPatient();
        BeanUtils.copyProperties(patient, impPatient);

        impPatient = patientDAO.save(impPatient);

        ResPatient resPatient = new ResPatient();
        BeanUtils.copyProperties(impPatient, resPatient);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resPatient);
    }

    @Override
    public BaseResponse getPatientCount(Predicate predicate) throws BaseException {
        QHsptPatient qHsptPatient = QHsptPatient.hsptPatient;
        Long count;
        count = getQueryFactory().
                select(qHsptPatient.patientId.count())
                .from(qHsptPatient)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse delPatient(Integer patientId) throws BaseException {
        //QHsptPatient qHsptPatient = QHsptPatient.hsptPatient;
        //HsptPatient patient = patientDAO.findOne(qHsptPatient.pkPatient.eq(patientId));
        HsptPatient patient = patientDAO.findByPatientId(patientId);
        if (null != patient) {
            patientDAO.delete((long) patient.getPatientId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updatePatient(ReqPatient reqPatient) throws BaseException {
        HsptPatient patient = patientDAO.findOne((long) reqPatient.getPatientId());
        if (null != patient) {
            BeanUtils.copyProperties(reqPatient, patient);

            patient = patientDAO.save(patient);
            ResPatient resPatient = new ResPatient();
            BeanUtils.copyProperties(patient, resPatient);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resPatient);
        }
        return new BaseResponse(StatusCode.DATA_NOT_FOUND);
    }

    @Override
    public BaseResponse getPatientAll(Predicate predicate) throws BaseException {
        QHsptPatient qHsptPatient = QHsptPatient.hsptPatient;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResPatient.class,
                        qHsptPatient.patientId,
                        qHsptPatient.patientType.patientTypeId,
                        qHsptPatient.name,
                        qHsptPatient.pwd,
                        qHsptPatient.appID,
                        qHsptPatient.openID,
                        qHsptPatient.uniqID,
                        qHsptPatient.outpatientID,
                        qHsptPatient.inpatientID,
                        qHsptPatient.phone,
                        qHsptPatient.plan.planId,
                        qHsptPatient.email,
                        qHsptPatient.doctor.pkUser,
                        qHsptPatient.addnDoctor.pkUser,
                        qHsptPatient.sex,
                        qHsptPatient.oldPatient,
                        qHsptPatient.state,
                        qHsptPatient.isReceivePicUploadSurvey,
                        qHsptPatient.birthday,
                        qHsptPatient.createTime
                ))
                .from(qHsptPatient)
                .where(predicate)
                .orderBy(qHsptPatient.patientId.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse getPatients(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptPatient qHsptPatient = QHsptPatient.hsptPatient;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResPatient.class,
                        qHsptPatient.patientId,
                        qHsptPatient.patientType.patientTypeId,
                        qHsptPatient.name,
                        qHsptPatient.pwd,
                        qHsptPatient.appID,
                        qHsptPatient.openID,
                        qHsptPatient.uniqID,
                        qHsptPatient.outpatientID,
                        qHsptPatient.inpatientID,
                        qHsptPatient.phone,
                        qHsptPatient.plan.planId,
                        qHsptPatient.email,
                        qHsptPatient.doctor.pkUser,
                        qHsptPatient.addnDoctor.pkUser,
                        qHsptPatient.sex,
                        qHsptPatient.oldPatient,
                        qHsptPatient.state,
                        qHsptPatient.isReceivePicUploadSurvey,
                        qHsptPatient.birthday,
                        qHsptPatient.createTime
                ))
                .from(qHsptPatient)
                .where(predicate)
                .orderBy(qHsptPatient.patientId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}
