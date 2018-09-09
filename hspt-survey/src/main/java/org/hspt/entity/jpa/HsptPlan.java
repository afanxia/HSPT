package org.hspt.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "Plan", schema = "hspt", catalog = "")
public class HsptPlan {

    @Id
    @Column(name = "planId")
    private Integer planId;    //自动编号

    @Basic
    @Column(name = "beginAge")
    private Integer beginAge;    //年龄区间下限

    @Basic
    @Column(name = "endAge")
    private Integer endAge;    //年龄区间上限

    @Basic
    @Column(name = "active")
    private Integer active;    //激活状态

    @Basic
    @Column(name = "sex")
    private Integer sex;    //性别: 1 male, 2 female, 3 either

    @Basic
    @Column(name = "oldPatient")
    private Integer oldPatient; //1 仅限新病例, 2 仅限既往病例, 3 仅限哮喘无忧用户, 4 不限

    @ManyToOne
    @JoinColumn(name = "patientTypeId")
    private HsptPatientType patientType;

    @ManyToOne
    @JoinColumn(name = "aid")
    private HsptUser doctor;    //操作医生

    @OneToMany()
    @OrderBy(value = "surveyId ASC")
    private Set<HsptSurvey> surveys = new HashSet<>();


    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getBeginAge() {
        return beginAge;
    }

    public void setBeginAge(Integer beginAge) {
        this.beginAge = beginAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public void setOldPatient(Integer oldPatient) {
        this.oldPatient = oldPatient;
    }

    public Integer getOldPatient() {
        return oldPatient;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public HsptPatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(HsptPatientType patientType) {
        this.patientType = patientType;
    }

    public HsptUser getDoctor() {
        return doctor;
    }

    public void setDoctor(HsptUser doctor) {
        this.doctor = doctor;
    }

    public Set<HsptSurvey> getSurveys() {
        return surveys;
    }

    public void setSurveys(Set<HsptSurvey> surveys) {
        this.surveys = surveys;
    }

    public HsptPlan() {

    }

    public HsptPlan(Integer beginAge, Integer endAge, Integer oldPatient, Integer sex, Integer active,
                HsptPatientType patientType, HsptUser doctor) {
        super();
        this.beginAge = beginAge;
        this.endAge = endAge;
        this.oldPatient = oldPatient;
        this.sex = sex;
        this.active = active;
        this.patientType = patientType;
        this.doctor = doctor;
    }


}
