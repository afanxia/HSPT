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
@Table(name = "RetrieveInfo", schema = "hspt", catalog = "")
public class HsptRetrieveInfo {

    @Id
    @OneToOne
    @JoinColumn(name = "deliveryId")
    private HsptDeliveryInfo deliveryInfo;

    @Basic
    @Column(name = "retrieveDate")
    private Date retrieveDate;    //答卷时间

    @ManyToOne
    @JoinColumn(name = "surveyId")
    private HsptSurvey survey;    //分发问卷

    @ManyToOne
    @JoinColumn(name = "patientId")
    private HsptPatient patient;    //分发病人

    @OneToMany(mappedBy = "RetrieveInfo", cascade = CascadeType.ALL)
    @OrderBy(value = "answerId ASC")
    private Set<HsptAnswer> answers = new HashSet<>();

    @Basic
    @Column(name = "byDoctor")
    private String byDoctor; //由该医生添加

    public String getByDoctor() {
        return byDoctor;
    }

    public void setByDoctor(String byDoctor) {
        this.byDoctor = byDoctor;
    }

    public Set<HsptAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<HsptAnswer> answers) {
        this.answers = answers;
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

    public HsptDeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HsptDeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public Date getRetrieveDate() {
        return retrieveDate;
    }

    public void setRetrieveDate(Date retrieveDate) {
        this.retrieveDate = retrieveDate;
    }

    public HsptRetrieveInfo() {
    }

}
