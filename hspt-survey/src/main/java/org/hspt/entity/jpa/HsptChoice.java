package org.hspt.entity.jpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "Choice", schema = "hspt", catalog = "")
public class HsptChoice {

    @Id
    @Column(name = "choiceId")
    private Integer choiceId;

    @Basic
    @Column(name = "score")
    private BigDecimal score;

    @Basic
    @Column(name = "choiceContent")
    private String choiceContent;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private HsptQuestion question;

    @Basic
    @Column(name = "choiceImgPath")
    private String choiceImgPath;

    public void setChoiceImgPath(String choiceImgPath) {
        this.choiceImgPath = choiceImgPath;
    }

    public String getChoiceImgPath() {
        return choiceImgPath;
    }

    public HsptQuestion getQuestion() { return question; }
    public void setQuestion(HsptQuestion question) { this.question = question; }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceContent() {
        return choiceContent;
    }

    public void setChoiceContent(String choiceContent) {
        this.choiceContent = choiceContent;
    }

    public HsptChoice() {
    }

    public HsptChoice(String choiceContent, BigDecimal score) {
        super();
        this.choiceContent = choiceContent;
        this.score = score;
    }

}
