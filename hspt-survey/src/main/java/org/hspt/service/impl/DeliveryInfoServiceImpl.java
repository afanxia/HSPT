package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.DeliveryInfoDAO;
import org.hspt.dao.jpa.SurveyDAO;
import org.hspt.entity.jpa.HsptDeliveryInfo;
import org.hspt.entity.jpa.HsptSurvey;
import org.hspt.entity.jpa.QHsptDeliveryInfo;
import org.hspt.entity.request.ReqDeliveryInfo;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResDeliveryInfo;
import org.hspt.service.DeliveryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <b> 提供问卷分发的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class DeliveryInfoServiceImpl extends BaseService implements DeliveryInfoService {

    @Autowired
    private DeliveryInfoDAO deliveryInfoDAO;

    @Autowired
    private SurveyDAO surveyDAO;


    @Override
    public BaseResponse addDeliveryInfo(ReqDeliveryInfo deliveryInfo) {
        if (null != deliveryInfoDAO.findByDeliveryId(deliveryInfo.getDeliveryId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptDeliveryInfo impDeliveryInfo = new HsptDeliveryInfo();
        BeanUtils.copyProperties(deliveryInfo, impDeliveryInfo);

        HsptSurvey survey = surveyDAO.findByPkSurvey(deliveryInfo.getSurveyId());
        if (null == survey ) {
            throw new BaseException(StatusCode.SURVEY_NOT_FOUND);
        }

        //得到该病人的最长答卷天数，过期将不再接受该次答卷
        Long maxDay = survey.getBday();//允许填卷天数

        //获得当前时间
        Date deliveryDate = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deliveryDate);
        calendar.add(Calendar.DAY_OF_MONTH, +maxDay.intValue());//+maxDay今天的时间加maxDay天

        // 根据最大分发天数,计算出截止日期
        Date endDate = calendar.getTime();
        impDeliveryInfo.setEndDate(deliveryDate);
        impDeliveryInfo.setEndDate(endDate);
        impDeliveryInfo = deliveryInfoDAO.save(impDeliveryInfo);

        ResDeliveryInfo resDeliveryInfo = new ResDeliveryInfo();
        BeanUtils.copyProperties(impDeliveryInfo, resDeliveryInfo);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resDeliveryInfo);
    }

    @Override
    public BaseResponse getDeliveryInfoCount(Predicate predicate) throws BaseException {
        QHsptDeliveryInfo qHsptDeliveryInfo = QHsptDeliveryInfo.hsptDeliveryInfo;
        Long count;
        count = getQueryFactory().
                select(qHsptDeliveryInfo.deliveryId.count())
                .from(qHsptDeliveryInfo)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getDeliveryInfoAll(Predicate predicate) throws BaseException {
        QHsptDeliveryInfo qHsptDeliveryInfo = QHsptDeliveryInfo.hsptDeliveryInfo;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResDeliveryInfo.class,
                        qHsptDeliveryInfo.deliveryId,
                        qHsptDeliveryInfo.survey.pkSurvey,
                        qHsptDeliveryInfo.patient.patientId,
                        qHsptDeliveryInfo.overday,
                        qHsptDeliveryInfo.state,
                        qHsptDeliveryInfo.deliveryDate,
                        qHsptDeliveryInfo.endDate
                ))
                .from(qHsptDeliveryInfo)
                .where(predicate)
                .orderBy(qHsptDeliveryInfo.deliveryId.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delDeliveryInfo(Integer deliveryId) throws BaseException {
        //QHsptDeliveryInfo qHsptDeliveryInfo = QHsptDeliveryInfo.hsptDeliveryInfo;
        //HsptDeliveryInfo deliveryInfo = deliveryInfoDAO.findOne(qHsptDeliveryInfo.pkDeliveryInfo.eq(deliveryInfoId));
        HsptDeliveryInfo deliveryInfo = deliveryInfoDAO.findByDeliveryId(deliveryId);
        if (null != deliveryInfo) {
            deliveryInfoDAO.delete((long) deliveryInfo.getDeliveryId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updateDeliveryInfo(HsptDeliveryInfo deliveryInfo) throws BaseException {
        deliveryInfo = deliveryInfoDAO.save(deliveryInfo);
        ResDeliveryInfo resDeliveryInfo = new ResDeliveryInfo();
        BeanUtils.copyProperties(deliveryInfo, resDeliveryInfo);

        return new BaseResponse(StatusCode.UPDATE_SUCCESS, resDeliveryInfo);
    }


    @Override
    public BaseResponse getDeliveryInfos(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptDeliveryInfo qHsptDeliveryInfo = QHsptDeliveryInfo.hsptDeliveryInfo;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResDeliveryInfo.class,
                        qHsptDeliveryInfo.deliveryId,
                        qHsptDeliveryInfo.survey.pkSurvey,
                        qHsptDeliveryInfo.patient.patientId,
                        qHsptDeliveryInfo.overday,
                        qHsptDeliveryInfo.state,
                        qHsptDeliveryInfo.deliveryDate,
                        qHsptDeliveryInfo.endDate
                ))
                .from(qHsptDeliveryInfo)
                .where(predicate)
                .orderBy(qHsptDeliveryInfo.deliveryId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}
