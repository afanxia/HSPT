package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.hspt.base.*;
import org.hspt.dao.jpa.SurveyDAO;
import org.hspt.dao.jpa.SurveyTypeDAO;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.ReqSurvey;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResSurvey;
import org.hspt.service.SurveyService;
import org.hspt.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <b> 提供问卷的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class SurveyServiceImpl extends BaseService implements SurveyService {

    @Autowired
    private SurveyDAO surveyDAO;

    @Autowired
    private SurveyTypeDAO surveyTypeDAO;


    @Override
    public BaseResponse addSurvey(ReqSurvey survey) {
        if (null != surveyDAO.findByPkSurvey(survey.getPkSurvey())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptSurvey impSurvey = new HsptSurvey();
        BeanUtils.copyProperties(survey, impSurvey);

        HsptSurveyType impSurveyType = surveyTypeDAO.findByPkSurveyType(survey.getPkSurveyType());
        if (null == impSurveyType ) {
            throw new BaseException(StatusCode.SURVEY_TYPE_NOT_FOUND);
        }

        impSurvey.setHsptSurveyType(impSurveyType);
        impSurvey = surveyDAO.save(impSurvey);

        ResSurvey resSurvey = new ResSurvey();
        BeanUtils.copyProperties(impSurvey, resSurvey);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resSurvey);
    }

    @Override
    public BaseResponse getSurveyCount(Predicate predicate) throws BaseException {
        QHsptSurvey qHsptSurvey = QHsptSurvey.hsptSurvey;
        long count;
        count = getQueryFactory().
                select(qHsptSurvey.pkSurvey.count())
                .from(qHsptSurvey)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getSurveyAll(Predicate predicate) throws BaseException {
        QHsptSurvey qHsptSurvey = QHsptSurvey.hsptSurvey;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResSurvey.class,
                        qHsptSurvey.pkSurvey,
                        qHsptSurvey.surveyType.pkSurveyType,
                        qHsptSurvey.surveyName,
                        qHsptSurvey.department,
                        qHsptSurvey.description,
                        qHsptSurvey.frequency,
                        qHsptSurvey.sendOnRegister
                ))
                .from(qHsptSurvey)
                .where(predicate)
                .orderBy(qHsptSurvey.pkSurvey.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delSurvey(long surveyId) throws BaseException {
        //QHsptSurvey qHsptSurvey = QHsptSurvey.hsptSurvey;
        //HsptSurvey survey = surveyDAO.findOne(qHsptSurvey.pkSurvey.eq(surveyId));
        HsptSurvey survey = surveyDAO.findByPkSurvey(surveyId);
        if (null != survey) {
            surveyDAO.delete((long) survey.getPkSurvey());
        }
        else {
            return new BaseResponse(StatusCode.SURVEY_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updateSurvey(ReqSurvey reqSurvey) throws BaseException {
        HsptSurvey survey = surveyDAO.findOne((long) reqSurvey.getPkSurvey());
        if (null != survey) {
            BeanUtils.copyProperties(reqSurvey, survey);

            HsptSurveyType impSurveyType = surveyTypeDAO.findByPkSurveyType(reqSurvey.getPkSurveyType());
            if (null == impSurveyType ) {
                throw new BaseException(StatusCode.SURVEY_TYPE_NOT_FOUND);
            }
            survey.setHsptSurveyType(impSurveyType);

            survey = surveyDAO.save(survey);
            ResSurvey resSurvey = new ResSurvey();
            BeanUtils.copyProperties(survey, resSurvey);
            reqSurvey.setPkSurveyType(reqSurvey.getPkSurveyType());

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resSurvey);
        }
        return new BaseResponse(StatusCode.SURVEY_NOT_FOUND);
    }


    @Override
    public BaseResponse getSurveys(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptSurvey qHsptSurvey = QHsptSurvey.hsptSurvey;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResSurvey.class,
                        qHsptSurvey.pkSurvey,
                        qHsptSurvey.surveyType.pkSurveyType,
                        qHsptSurvey.surveyName,
                        qHsptSurvey.department,
                        qHsptSurvey.description,
                        qHsptSurvey.frequency,
                        qHsptSurvey.sendOnRegister
                ))
                .from(qHsptSurvey)
                .where(predicate)
                .orderBy(qHsptSurvey.pkSurvey.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

}
