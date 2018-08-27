package org.hspt.entity.jpa;

import javax.persistence.*;
import java.util.Date;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "hospital", schema = "hspt", catalog = "")
public class HsptHospital {

    @Id
    @Column(name = "hospitalId")
    private Integer hospitalId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "visible")
    private boolean visible;

    @Basic
    @Column(name = "cityId")
    private Integer cityId;

    public Integer getHospitalId() { return hospitalId; }
    public void setHospitalId(Integer hospitalId) { this.hospitalId = hospitalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityId() {
        return cityId;
    }

}
