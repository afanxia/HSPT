package org.hspt.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.base.BaseService;
import org.hspt.base.StatusCode;
import org.hspt.dao.jpa.PlanDAO;
import org.hspt.dao.jpa.SurveyDAO;
import org.hspt.entity.dto.SurveyDTO;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.ReqPlan;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResPlan;
import org.hspt.service.PlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b> 提供计划的相关操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class PlanServiceImpl extends BaseService implements PlanService {

    @Autowired
    private PlanDAO planDAO;

    @Autowired
    private SurveyDAO surveyDAO;


    @Override
    public BaseResponse addPlan(ReqPlan plan) {
        if (null != planDAO.findByPlanId(plan.getPlanId())) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }
        HsptPlan impPlan = new HsptPlan();
        BeanUtils.copyProperties(plan, impPlan);

        impPlan = planDAO.save(impPlan);

        ResPlan resPlan = new ResPlan();
        BeanUtils.copyProperties(impPlan, resPlan);

        return new BaseResponse(StatusCode.ADD_SUCCESS, resPlan);
    }

    @Override
    public BaseResponse getPlanCount(Predicate predicate) throws BaseException {
        QHsptPlan qHsptPlan = QHsptPlan.hsptPlan;
        Long count;
        count = getQueryFactory().
                select(qHsptPlan.planId.count())
                .from(qHsptPlan)
                .where(predicate)
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse getPlanAll(Predicate predicate) throws BaseException {
        List<HsptPlan> plans = planDAO.findAll();
        List<ResPlan> resPlans = new ArrayList<>();

        for(HsptPlan plan : plans) {
            ResPlan p = new ResPlan();
            p.setPlanId(plan.getPlanId());
            p.setBeginAge(plan.getBeginAge());
            p.setEndAge(plan.getEndAge());
            p.setActive(plan.getActive());
            p.setSex(plan.getSex());
            p.setOldPatient(plan.getOldPatient());
            p.setPatientTypeId(plan.getPatientType().getPatientTypeId());
            p.setAid(plan.getDoctor().getPkUser());

            Set<SurveyDTO> surveyDTOS = new HashSet<>();
            Set<HsptSurvey> surveys = plan.getSurveys();
            for(HsptSurvey survey : surveys){
                SurveyDTO s = new SurveyDTO();
                BeanUtils.copyProperties(survey, s);
                surveyDTOS.add(s);
            }
            p.setSurveys(surveyDTOS);
            resPlans.add(p);
        }

        //使用自定义方式查询结果
        return new BaseResponse(resPlans);
    }

    @Override
    public BaseResponse delPlan(Integer planId) throws BaseException {
        //QHsptPlan qHsptPlan = QHsptPlan.hsptPlan;
        //HsptPlan plan = planDAO.findOne(qHsptPlan.pkPlan.eq(planId));
        HsptPlan plan = planDAO.findByPlanId(planId);
        if (null != plan) {
            planDAO.delete((long) plan.getPlanId());
        }
        else {
            return new BaseResponse(StatusCode.DATA_NOT_FOUND);
        }
        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }

    @Override
    public BaseResponse updatePlan(ReqPlan reqPlan) throws BaseException {
        HsptPlan plan = planDAO.findOne((long) reqPlan.getPlanId());
        if (null != plan) {
            BeanUtils.copyProperties(reqPlan, plan);

            plan = planDAO.save(plan);
            ResPlan resPlan = new ResPlan();
            BeanUtils.copyProperties(plan, resPlan);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS, resPlan);
        }
        return new BaseResponse(StatusCode.DATA_NOT_FOUND);
    }


    @Override
    public BaseResponse getPlans(Pageable pageable, Predicate predicate) throws BaseException {

        QHsptPlan qHsptPlan = QHsptPlan.hsptPlan;
        List<HsptPlan> plans = getQueryFactory()
                .select(qHsptPlan)
                .from(qHsptPlan)
                .where(predicate)
                .orderBy(qHsptPlan.planId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ResPlan> resPlans = new ArrayList<>();

        for(HsptPlan plan : plans) {
            ResPlan p = new ResPlan();
            p.setPlanId(plan.getPlanId());
            p.setBeginAge(plan.getBeginAge());
            p.setEndAge(plan.getEndAge());
            p.setActive(plan.getActive());
            p.setSex(plan.getSex());
            p.setOldPatient(plan.getOldPatient());
            p.setPatientTypeId(plan.getPatientType().getPatientTypeId());
            p.setAid(plan.getDoctor().getPkUser());

            Set<SurveyDTO> surveyDTOS = new HashSet<>();
            Set<HsptSurvey> surveys = plan.getSurveys();
            for(HsptSurvey survey : surveys){
                SurveyDTO s = new SurveyDTO();
                BeanUtils.copyProperties(survey, s);
                surveyDTOS.add(s);
            }
            p.setSurveys(surveyDTOS);
            resPlans.add(p);
        }

        //使用自定义方式查询结果
        return new BaseResponse(resPlans);
    }

}
