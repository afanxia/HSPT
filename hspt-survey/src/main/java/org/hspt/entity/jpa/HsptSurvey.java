package org.hspt.entity.jpa;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "survey", schema = "hspt", catalog = "")
public class HsptSurvey {

    @Id
    @Column(name = "surveyId")
    private long pkSurvey;    //问卷编号

    @ManyToOne
    @JoinColumn(name = "typeId")
    private HsptSurveyType surveyType;    //问卷类型

    @Basic
    @Column(name = "surveyName")
    private String surveyName;    //问卷名称

    @Basic
    @Column(name = "author")
    private String author;    //作者名称

    @Basic
    @Column(name = "department")
    private String department;    //科室

    @Basic
    @Column(name = "putdate")
    private Date putdate;    //生成日期

    @Basic
    @Column(name = "num")
    private long num;    //总分发数

    @Basic
    @Column(name = "currentNum")
    private long currentNum;    //总回收数

    @Basic
    @Column(name = "description")
    private String description;    //简介

    //private Doctor doctor;    //操作医生

    @Basic
    @Column(name = "frequency")
    private long frequency;    //随访频率

    @Basic
    @Column(name = "times")
    private long times;    //随访次数

    @Basic
    @Column(name = "sendOnRegister")
    private boolean sendOnRegister;

    //private Set<Question> questions = new HashSet<>();

    @Basic
    @Column(name = "bday")
    private long bday;  // overdue day



    public void setBday(long bday) {
        this.bday = bday;
    }

    public long getBday() {
        return bday;
    }

    public void setSendOnRegister(boolean sendOnRegister) {
        this.sendOnRegister = sendOnRegister;
    }

    public boolean isSendOnRegister() {
        return sendOnRegister;
    }

    public long getPkSurvey() {
        return pkSurvey;
    }

    public void setPkSurvey(long pkSurvey) {
        this.pkSurvey = pkSurvey;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    //public Set<Question> getQuestions() {
    //    return questions;
    //}

    //public void setQuestions(Set<Question> questions) {
    //    this.questions = questions;
    //}

    public HsptSurveyType getHsptSurveyType() {
        return surveyType;
    }

    public void setHsptSurveyType(HsptSurveyType surveyType) {
        this.surveyType = surveyType;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getPutdate() {
        return putdate;
    }

    public void setPutdate(Date putdate) {
        this.putdate = putdate;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(long currentNum) {
        this.currentNum = currentNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //public Doctor getDoctor() {
    //    return doctor;
    //}

    //public void setDoctor(Doctor doctor) {
    //    this.doctor = doctor;
    //}


    //public void Survey(SurveyType surveyType, String surveyName, String author,
    //              String department, Date putdate, String description,
    //              Doctor doctor, long frequency, long times, boolean sendOnRegister, long bday) {
    //    super();
    //    this.surveyType = surveyType;
    //    this.surveyName = surveyName;
    //    this.author = author;
    //    this.department = department;
    //    this.putdate = putdate;
    //    this.description = description;
    //    this.doctor = doctor;
    //    this.frequency = frequency;
    //    this.times = times;
    //    this.sendOnRegister = sendOnRegister;
    //    this.bday = bday;
    //}

    //public void Survey(SurveyType surveyType, String surveyName, String author,
    //              String department) {
    //    super();
    //    this.surveyType = surveyType;
    //    this.surveyName = surveyName;
    //    this.author = author;
    //    this.department = department;
    //}


}
