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
@Table(name = "Answer", schema = "hspt", catalog = "")
public class HsptAnswer {

    @Id
    @Column(name = "answerId")
    private Integer answerId;

    @Basic
    @Column(name = "lastModified")
    private String lastModified; //最后修改人

    @Basic
    @Column(name = "modifiedDate")
    private String modifiedDate;//最后修改日期

    @ManyToOne
    @Column(name = "surveyId")
    private HsptSurvey survey;    //分发问卷

    @ManyToOne
    @Column(name = "patientId")
    private HsptPatient patient;    //分发病人

    @Basic
    @Column(name = "textChoiceContent")
    private String textChoiceContent;

    @Basic
    @Column(name = "textChoice")
    private Integer textChoice;

    @ManyToOne
    @Column(name = "deliveryId")
    private HsptRetrieveInfo retrieveInfo;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private HsptQuestion question;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "answer_choice", joinColumns = @JoinColumn(name = "answerId", referencedColumnName = "answerId"), inverseJoinColumns = @JoinColumn(name = "choiceId", referencedColumnName = "choiceId"))
    private Set<HsptChoice> choices = new HashSet<>();

    public Set<HsptChoice> getChoices() {
        return choices;
    }

    public void setChoices(Set<HsptChoice> choices) {
        this.choices = choices;
    }

    public HsptQuestion getQuestion() {
        return question;
    }

    public void setQuestion(HsptQuestion question) {
        this.question = question;
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

    public HsptRetrieveInfo getRetrieveInfo() {
        return retrieveInfo;
    }

    public void setRetrieveInfo(HsptRetrieveInfo retrieveInfo) {
        this.retrieveInfo = retrieveInfo;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getTextChoice() {
        return textChoice;
    }

    public void setTextChoice(Integer textChoice) {
        this.textChoice = textChoice;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    public String getTextChoiceContent() { return textChoiceContent; }
    public void setTextChoiceContent(String textChoiceContent) { this.textChoiceContent = textChoiceContent; }


}
