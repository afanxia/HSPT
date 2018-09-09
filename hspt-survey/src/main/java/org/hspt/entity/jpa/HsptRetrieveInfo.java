package org.hspt.entity.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "RetrieveInfo", schema = "hspt", catalog = "")
public class HsptRetrieveInfo implements Serializable {

    @Id
    @Column(name = "deliveryId")
    private Integer deliveryId;

    @OneToOne
    @PrimaryKeyJoinColumn
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


    @Basic
    @Column(name = "byDoctor")
    private String byDoctor; //由该医生添加

	public int getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

    public String getByDoctor() {
        return byDoctor;
    }

    public void setByDoctor(String byDoctor) {
        this.byDoctor = byDoctor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HsptRetrieveInfo that = (HsptRetrieveInfo) o;
        return Objects.equals(deliveryInfo, that.deliveryInfo) &&
                Objects.equals(retrieveDate, that.retrieveDate) &&
                Objects.equals(survey, that.survey) &&
                Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deliveryInfo, retrieveDate, survey, patient);
    }

    private static final long serialVersionUID = 1L;


}
