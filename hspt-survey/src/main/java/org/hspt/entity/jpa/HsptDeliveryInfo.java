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
@Table(name = "deliveryInfo", schema = "hspt", catalog = "")
public class HsptDeliveryInfo {

    @Id
    @Column(name = "deliveryId")
    private Integer deliveryId;    //分发编号

    @ManyToOne
    @JoinColumn(name = "surveyId")
    private HsptSurvey survey;    //分发问卷

    @ManyToOne
    @JoinColumn(name = "patientId")
    private HsptPatient patient;    //分发病人

    @Basic
    @Column(name = "deliveryDate")
    private Date deliveryDate;    //分发日期

    @Basic
    @Column(name = "endDate")
    private Date endDate;    //截止日期

    @Basic
    @Column(name = "overday")
    private Integer overday;    //逾期天数

    @Basic
    @Column(name = "state")
    private Integer state; //状态 (已简化为： 未答卷=0,重发未答卷>0[次数])

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "deliveryInfo")
    @PrimaryKeyJoinColumn
    private HsptRetrieveInfo retrieveInfo;

    @OneToMany(mappedBy = "deliveryInfo", cascade = CascadeType.ALL)
    @OrderBy(value = "answerId ASC")
    private Set<HsptAnswer> answers = new HashSet<>();

    public HsptRetrieveInfo getRetrieveInfo() {
        return retrieveInfo;
    }

    public void setRetrieveInfo(HsptRetrieveInfo retrieveInfo) {
        this.retrieveInfo = retrieveInfo;
    }

    public Integer getOverday() {
        return overday;
    }

    public void setOverday(Integer overday) {
        this.overday = overday;
    }


    public Set<HsptAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<HsptAnswer> answers) {
        this.answers = answers;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public HsptSurvey getSurvey() {
        return survey;
    }

    public void setSurvey(HsptSurvey survey) {
        this.survey = survey;
    }

    public HsptPatient getPatient() {
        return patient;
    }

    public void setPatient(HsptPatient patient) {
        this.patient = patient;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public HsptDeliveryInfo() {

    }

}
