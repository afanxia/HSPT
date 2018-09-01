package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.RetrieveInfoDAO;
import org.hspt.dao.jpa.SurveyDAO;
import org.hspt.entity.jpa.HsptRetrieveInfo;
import org.hspt.entity.jpa.HsptSurvey;
import org.hspt.entity.jpa.QHsptRetrieveInfo;
import org.hspt.entity.request.ReqRetrieveInfo;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResRetrieveInfo;
import org.hspt.service.RetrieveInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <b> 提供问卷回收的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class RetrieveInfoServiceImpl extends BaseService implements RetrieveInfoService {

    @Autowired
    private RetrieveInfoDAO retrieveInfoDAO;

    @Autowired
    private SurveyDAO surveyDAO;


    @Override
    public BaseResponse addRetrieveInfo(ReqRetrieveInfo retrieveInfo) {
        if (null != retrieveInfoDAO.findByDeliveryId(retrieveInfo.getDeliveryId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptRetrieveInfo impRetrieveInfo = new HsptRetrieveInfo();
        BeanUtils.copyProperties(retrieveInfo, impRetrieveInfo);

        HsptSurvey survey = surveyDAO.findByPkSurvey(retrieveInfo.getSurveyId());
        if (null == survey ) {
            throw new BaseException(StatusCode.SURVEY_NOT_FOUND);
        }

        survey.setCurrentNum(survey.getCurrentNum() + 1);
        survey = surveyDAO.save(survey);

        //获得当前时间
        Date retrieveDate = new Date(System.currentTimeMillis());

        impRetrieveInfo.setSurvey(survey);
        impRetrieveInfo.setRetrieveDate(retrieveDate);
        impRetrieveInfo = retrieveInfoDAO.save(impRetrieveInfo);

        ResRetrieveInfo resRetrieveInfo = new ResRetrieveInfo();
        BeanUtils.copyProperties(impRetrieveInfo, resRetrieveInfo);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resRetrieveInfo);
    }

    @Override
    public BaseResponse getRetrieveInfoCount(Predicate predicate) throws BaseException {
        QHsptRetrieveInfo qHsptRetrieveInfo = QHsptRetrieveInfo.hsptRetrieveInfo;
        Long count;
        count = getQueryFactory().
                select(qHsptRetrieveInfo.deliveryInfo.deliveryId.count())
                .from(qHsptRetrieveInfo)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getRetrieveInfoAll(Predicate predicate) throws BaseException {
        QHsptRetrieveInfo qHsptRetrieveInfo = QHsptRetrieveInfo.hsptRetrieveInfo;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResRetrieveInfo.class,
                        qHsptRetrieveInfo.deliveryInfo.deliveryId,
                        qHsptRetrieveInfo.retrieveDate,
                        qHsptRetrieveInfo.survey.pkSurvey,
                        qHsptRetrieveInfo.patient.patientId,
                        qHsptRetrieveInfo.byDoctor
                ))
                .from(qHsptRetrieveInfo)
                .where(predicate)
                .orderBy(qHsptRetrieveInfo.deliveryInfo.deliveryId.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delRetrieveInfo(Integer deliveryId) throws BaseException {
        //QHsptRetrieveInfo qHsptRetrieveInfo = QHsptRetrieveInfo.hsptRetrieveInfo;
        //HsptRetrieveInfo retrieveInfo = retrieveInfoDAO.findOne(qHsptRetrieveInfo.pkRetrieveInfo.eq(retrieveInfoId));
        HsptRetrieveInfo retrieveInfo = retrieveInfoDAO.findByDeliveryId(deliveryId);
        if (null != retrieveInfo) {
            retrieveInfoDAO.delete((long) retrieveInfo.getDeliveryInfo().getDeliveryId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updateRetrieveInfo(HsptRetrieveInfo retrieveInfo) throws BaseException {
        retrieveInfo = retrieveInfoDAO.save(retrieveInfo);
        ResRetrieveInfo resRetrieveInfo = new ResRetrieveInfo();
        BeanUtils.copyProperties(retrieveInfo, resRetrieveInfo);

        return new BaseResponse(StatusCode.UPDATE_SUCCESS, resRetrieveInfo);
    }


    @Override
    public BaseResponse getRetrieveInfos(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptRetrieveInfo qHsptRetrieveInfo = QHsptRetrieveInfo.hsptRetrieveInfo;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResRetrieveInfo.class,
                        qHsptRetrieveInfo.deliveryInfo.deliveryId,
                        qHsptRetrieveInfo.retrieveDate,
                        qHsptRetrieveInfo.survey.pkSurvey,
                        qHsptRetrieveInfo.patient.patientId,
                        qHsptRetrieveInfo.byDoctor
                ))
                .from(qHsptRetrieveInfo)
                .where(predicate)
                .orderBy(qHsptRetrieveInfo.deliveryInfo.deliveryId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}