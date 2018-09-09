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
@Table(name = "Question", schema = "hspt", catalog = "")
public class HsptQuestion {

    @Id
    @Column(name = "questionId")
    private Integer questionId;

    @Basic
    @Column(name = "sortId")
    private Integer sortId;

    @Basic
    @Column(name = "surveyId")
    private Integer surveyId;

    @Basic
    @Column(name = "questionContent")
    private String questionContent;

    @Basic
    @Column(name = "textChoice")
    private Integer textChoice;

    @Basic
    @Column(name = "questionType")
    private Integer questionType;

    @Basic
    @Column(name = "startAge")
    private Integer startAge;

    @Basic
    @Column(name = "endAge")
    private Integer endAge;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy(value = "choiceId ASC")
    private Set<HsptChoice> choices = new HashSet<>();

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getStartAge() {
        return startAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public Set<HsptChoice> getChoices() {
        return choices;
    }

    public void setChoices(Set<HsptChoice> choices) {
        this.choices = choices;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getTextChoice() {
        return textChoice;
    }

    public void setTextChoice(Integer textChoice) {
        this.textChoice = textChoice;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public HsptQuestion() {

    }

    public HsptQuestion(Integer surveyId, String questionContent,
                    Integer questionType, Integer textChoice, Integer startAge, Integer endAge) {
        super();
        this.surveyId = surveyId;
        this.questionContent = questionContent;
        this.questionType = questionType;
        this.textChoice = textChoice;
        this.startAge = startAge;
        this.endAge = endAge;
    }
}
