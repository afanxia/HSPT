package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.*;
import org.springframework.data.domain.Pageable;

/**
 * <b> 问卷管理服务 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface SurveyService {

    /**
     * 添加问卷
     *
     * @param survey
     * @return
     */
    BaseResponse addSurvey(ReqSurvey survey) throws BaseException;

    /**
     * 修改问卷
     *
     * @param survey
     * @return
     */
    BaseResponse updateSurvey(ReqSurvey survey) throws BaseException;

    /**
     * 删除问卷
     *
     * @param surveyId
     * @return
     */
    BaseResponse delSurvey(Integer surveyId) throws BaseException;


    /**
     * 获取问卷列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getSurveyAll(Predicate predicate) throws BaseException;

    /**
     * 获取问卷列表
     *
     * @param pageable 分页信息
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getSurveys(Pageable pageable, Predicate predicate) throws BaseException;

    /**
     * 获取问卷总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getSurveyCount(Predicate predicate) throws BaseException;


}
