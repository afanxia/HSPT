package org.hspt.entity.jpa;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "surveyType", schema = "hspt", catalog = "")
public class HsptSurveyType {

    @Id
    @Column(name = "typeId")
    private long pkSurveyType; //问卷类型编号

    @Basic
    @Column(name = "typeName")
    private String surveyTypeName;    //问卷类型名称


    public long getPkSurveyType() {
        return pkSurveyType;
    }

    public void setPkSurveyType(long pkSurveyType) {
        this.pkSurveyType = pkSurveyType;
    }

    public String getSurveyTypeName() {
        return surveyTypeName;
    }

    public void setSurveyTypeName(String surveyTypeName) {
        this.surveyTypeName = surveyTypeName;
    }

}
