package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import org.hspt.base.*;
import org.hspt.dao.jpa.SurveyTypeDAO;
import org.hspt.entity.jpa.HsptSurveyType;
import org.hspt.entity.jpa.QHsptSurveyType;
import org.hspt.entity.request.ReqSurveyType;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResSurveyType;
import org.hspt.service.SurveyTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b> 提供问卷类型的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class SurveyTypeServiceImpl extends BaseService implements SurveyTypeService {

    @Autowired
    private SurveyTypeDAO surveyTypeDAO;


    @Override
    public BaseResponse addSurveyType(ReqSurveyType surveyType) {
        if (null != surveyTypeDAO.findByPkSurveyType(surveyType.getPkSurveyType())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptSurveyType impSurveyType = new HsptSurveyType();
        BeanUtils.copyProperties(surveyType, impSurveyType);

        impSurveyType = surveyTypeDAO.save(impSurveyType);

        ResSurveyType resSurveyType = new ResSurveyType();
        BeanUtils.copyProperties(impSurveyType, resSurveyType);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resSurveyType);
    }

    @Override
    public BaseResponse getSurveyTypeCount(Predicate predicate) throws BaseException {
        QHsptSurveyType qHsptSurveyType = QHsptSurveyType.hsptSurveyType;
        long count;
        count = getQueryFactory().
                select(qHsptSurveyType.pkSurveyType.count())
                .from(qHsptSurveyType)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getSurveyTypeAll(Predicate predicate) throws BaseException {
        QHsptSurveyType qHsptSurveyType = QHsptSurveyType.hsptSurveyType;
        List list = getQueryFactory()
                .select(Projections.bean(
                        ResSurveyType.class,
                        qHsptSurveyType.pkSurveyType,
                        qHsptSurveyType.surveyTypeName
                ))
                .from(qHsptSurveyType)
                .where(predicate)
                .orderBy(qHsptSurveyType.pkSurveyType.asc())
                .fetch();

        //使用自定义方式查询结果
        return new BaseResponse(list);
    }

    @Override
    public BaseResponse delSurveyType(Integer surveyTypeId) throws BaseException {
        QHsptSurveyType qHsptSurveyType = QHsptSurveyType.hsptSurveyType;
        HsptSurveyType surveyType = surveyTypeDAO.findOne(qHsptSurveyType.pkSurveyType.eq(surveyTypeId));
        if (null != surveyType) {
            surveyTypeDAO.delete((long) surveyType.getPkSurveyType());
        }
        else {
            return new BaseResponse(StatusCode.SURVEY_TYPE_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updateSurveyType(ReqSurveyType reqSurveyType) throws BaseException {
        HsptSurveyType surveyType = surveyTypeDAO.findOne((long) reqSurveyType.getPkSurveyType());
        if (null != surveyType) {
            BeanUtils.copyProperties(reqSurveyType, surveyType);

            surveyType = surveyTypeDAO.save(surveyType);
            ResSurveyType resSurveyType = new ResSurveyType();
            BeanUtils.copyProperties(surveyType, resSurveyType);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resSurveyType);
        }
        return new BaseResponse(StatusCode.SURVEY_TYPE_NOT_FOUND);
    }


}
