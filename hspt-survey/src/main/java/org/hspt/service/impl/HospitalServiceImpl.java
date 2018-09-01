package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.HospitalDAO;
import org.hspt.entity.jpa.HsptHospital;
import org.hspt.entity.jpa.QHsptHospital;
import org.hspt.entity.request.ReqHospital;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResHospital;
import org.hspt.service.HospitalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b> 提供医院的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class HospitalServiceImpl extends BaseService implements HospitalService {

    @Autowired
    private HospitalDAO hospitalDAO;


    @Override
    public BaseResponse addHospital(ReqHospital hospital) {
        if (null != hospitalDAO.findByHospitalId(hospital.getHospitalId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptHospital impHospital = new HsptHospital();
        BeanUtils.copyProperties(hospital, impHospital);

        impHospital = hospitalDAO.save(impHospital);

        ResHospital resHospital = new ResHospital();
        BeanUtils.copyProperties(impHospital, resHospital);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resHospital);
    }

    @Override
    public BaseResponse getHospitalCount(Predicate predicate) throws BaseException {
        QHsptHospital qHsptHospital = QHsptHospital.hsptHospital;
        Long count;
        count = getQueryFactory().
                select(qHsptHospital.hospitalId.count())
                .from(qHsptHospital)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getHospitalAll(Predicate predicate) throws BaseException {
        QHsptHospital qHsptHospital = QHsptHospital.hsptHospital;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResHospital.class,
                        qHsptHospital.hospitalId,
                        qHsptHospital.name
                ))
                .from(qHsptHospital)
                .where(predicate)
                .orderBy(qHsptHospital.hospitalId.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delHospital(Integer hospitalId) throws BaseException {
        //QHsptHospital qHsptHospital = QHsptHospital.hsptHospital;
        //HsptHospital hospital = hospitalDAO.findOne(qHsptHospital.hospitalId.eq(hospitalId));
        HsptHospital hospital = hospitalDAO.findByHospitalId(hospitalId);
        if (null != hospital) {
            hospitalDAO.delete((long) hospital.getHospitalId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updateHospital(ReqHospital reqHospital) throws BaseException {
        HsptHospital hospital = hospitalDAO.findOne((long) reqHospital.getHospitalId());
        if (null != hospital) {
            BeanUtils.copyProperties(reqHospital, hospital);

            hospital = hospitalDAO.save(hospital);
            ResHospital resHospital = new ResHospital();
            BeanUtils.copyProperties(hospital, resHospital);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resHospital);
        }
        return new BaseResponse(StatusCode.DATA_NOT_FOUND);
    }


}
