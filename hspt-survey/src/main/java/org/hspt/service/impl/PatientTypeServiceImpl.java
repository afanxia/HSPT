package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.PatientTypeDAO;
import org.hspt.entity.jpa.HsptPatientType;
import org.hspt.entity.jpa.QHsptPatientType;
import org.hspt.entity.request.ReqPatientType;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPatientType;
import org.hspt.service.PatientTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b> 提供病人类型的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class PatientTypeServiceImpl extends BaseService implements PatientTypeService {

    @Autowired
    private PatientTypeDAO patientTypeDAO;


    @Override
    public BaseResponse addPatientType(ReqPatientType patientType) {
        if (null != patientTypeDAO.findByPatientTypeId(patientType.getPatientTypeId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptPatientType impPatientType = new HsptPatientType();
        BeanUtils.copyProperties(patientType, impPatientType);

        impPatientType = patientTypeDAO.save(impPatientType);

        ResPatientType resPatientType = new ResPatientType();
        BeanUtils.copyProperties(impPatientType, resPatientType);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resPatientType);
    }

    @Override
    public BaseResponse getPatientTypeCount(Predicate predicate) throws BaseException {
        QHsptPatientType qHsptPatientType = QHsptPatientType.hsptPatientType;
        Long count;
        count = getQueryFactory().
                select(qHsptPatientType.patientTypeId.count())
                .from(qHsptPatientType)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getPatientTypeAll(Predicate predicate) throws BaseException {
        QHsptPatientType qHsptPatientType = QHsptPatientType.hsptPatientType;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResPatientType.class,
                        qHsptPatientType.patientTypeId,
                        qHsptPatientType.patientTypeName
                ))
                .from(qHsptPatientType)
                .where(predicate)
                .orderBy(qHsptPatientType.patientTypeId.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delPatientType(Integer patientTypeId) throws BaseException {
        //QHsptPatientType qHsptPatientType = QHsptPatientType.hsptPatientType;
        //HsptPatientType patientType = patientTypeDAO.findOne(qHsptPatientType.patientTypeId.eq(patientTypeId));
        HsptPatientType patientType = patientTypeDAO.findByPatientTypeId(patientTypeId);
        if (null != patientType) {
            patientTypeDAO.delete((long) patientType.getPatientTypeId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updatePatientType(ReqPatientType reqPatientType) throws BaseException {
        HsptPatientType patientType = patientTypeDAO.findOne((long) reqPatientType.getPatientTypeId());
        if (null != patientType) {
            BeanUtils.copyProperties(reqPatientType, patientType);

            patientType = patientTypeDAO.save(patientType);
            ResPatientType resPatientType = new ResPatientType();
            BeanUtils.copyProperties(patientType, resPatientType);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resPatientType);
        }
        return new BaseResponse(StatusCode.DATA_NOT_FOUND);
    }


    @Override
    public BaseResponse getPatientTypes(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptPatientType qHsptPatientType = QHsptPatientType.hsptPatientType;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResPatientType.class,
                        qHsptPatientType.patientTypeId,
                        qHsptPatientType.patientTypeName
                ))
                .from(qHsptPatientType)
                .where(predicate)
                .orderBy(qHsptPatientType.patientTypeId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}