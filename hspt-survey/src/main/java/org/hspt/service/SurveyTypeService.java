package org.hspt.service;

import com.querydsl.core.types.Predicate;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqSurveyType;

/**
 * <b> 提供问卷类型的维护管理 </b>
 * <p>
 * 功能描述:
 * </p>
 */
public interface SurveyTypeService {

    /**
     * 添加问卷类型
     *
     * @param surveyType
     * @return
     */
    BaseResponse addSurveyType(ReqSurveyType surveyType) throws BaseException;

    /**
     * 修改问卷类型
     *
     * @param surveyType
     * @return
     */
    BaseResponse updateSurveyType(ReqSurveyType surveyType) throws BaseException;

    /**
     * 删除问卷类型
     *
     * @param surveyTypeId
     * @return
     */
    BaseResponse delSurveyType(Integer surveyTypeId) throws BaseException;


    /**
     * 获取问卷类型列表
     *
     * @param predicate     过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getSurveyTypeAll(Predicate predicate) throws BaseException;


    /**
     * 获取问卷类型总数
     *
     * @param predicate  过滤条件
     * @return
     * @throws BaseException
     */
    BaseResponse getSurveyTypeCount(Predicate predicate) throws BaseException;


}
