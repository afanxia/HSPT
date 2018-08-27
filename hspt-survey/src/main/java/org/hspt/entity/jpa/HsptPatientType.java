package org.hspt.entity.jpa;

import javax.persistence.*;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "patientType", schema = "hspt", catalog = "")
public class HsptPatientType {

    @Id
    @Column(name = "patientTypeId")
    private long patientTypeId; //问卷类型编号

    @Basic
    @Column(name = "patientTypeName")
    private String patientTypeName;    //问卷类型名称


    public long getPatientTypeId() {
        return patientTypeId;
    }

    public void setPatientTypeId(long patientTypeId) {
        this.patientTypeId = patientTypeId;
    }

    public String getPatientTypeName() {
        return patientTypeName;
    }

    public void setPatientTypeName(String patientTypeName) {
        this.patientTypeName = patientTypeName;
    }

}
