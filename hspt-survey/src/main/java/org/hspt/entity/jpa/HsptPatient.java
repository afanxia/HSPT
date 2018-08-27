package org.hspt.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Entity
@Table(name = "Patient", schema = "hspt", catalog = "")
public class HsptPatient {

    @Id
    @Column(name = "patientId")
    private Integer patientId;    //自动编号

    @Basic
    @Column(name = "name")
    private String name;    //真实名称

    @Basic
    @Column(name = "pwd")
    private String pwd;    //密码

    @Basic
    @Column(name = "appID")
    private String appID; //微信appID

    @Basic
    @Column(name = "openID")
    private String openID;

    @Basic
    @Column(name = "uniqID")
    private String uniqID;

    @Basic
    @Column(name = "outpatientID")
    private String outpatientID;

    @Basic
    @Column(name = "inpatientID")
    private String inpatientID;

    @Basic
    @Column(name = "phone")
    private String phone;    //联系方式

    @ManyToOne
    @JoinColumn(name = "patientTypeId")
    private HsptPatientType patientType;    //病人类型(病人或者病人家属)

    @ManyToOne
    @JoinColumn(name = "planId")
    private HsptPlan plan;    //随访计划

    @Basic
    @Column(name = "email")
    private String email;    //邮箱

    @Basic
    @Column(name = "aid")
    private HsptUser doctor;    //操作医生

    @Basic
    @Column(name = "sex")
    private Integer sex;      //  male == 1, female == 0; different from sex in Plan

    @Basic
    @Column(name = "addnDoctorId")
    private HsptUser addnDoctor;    //共享医生

    @Basic
    @Column(name = "oldPatient")
    private Integer oldPatient;  // 1 新病例, 2 既往病例, 3 哮喘无忧用户

    @Basic
    @Column(name = "state")
    private Integer state; // 状态， -1为未激活（出现在回收站且不发送问卷），

    @Basic
    @Column(name = "birthday")
    private Date birthday;

    @Basic
    @Column(name = "createTime")
    private Date createTime;    //创建时间

    @OneToMany(mappedBy = "Patient", cascade = CascadeType.ALL)
    @OrderBy(value = "deliveryId ASC")
    private Set<HsptDeliveryInfo> deliveryInfos;    //该病人的随访问卷分发信息

    @OneToMany(mappedBy = "Patient", cascade = CascadeType.ALL)
    @OrderBy(value = "deliveryId ASC")
    private Set<HsptRetrieveInfo> retrieveInfos;    //该病人的答卷信息


    public Integer getState() { return state; }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOldPatient() {
        return oldPatient;
    }

    public void setOldPatient(Integer oldPatient) {
        this.oldPatient = oldPatient;
    }


    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getUniqID() {
        return uniqID;
    }

    public void setUniqID(String uniqID) {
        this.uniqID = uniqID;
    }

    public String getOutpatientID() {
        return outpatientID;
    }

    public void setOutpatientID(String outpatientID) {
        this.outpatientID = outpatientID;
    }

    public String getInpatientID() {
        return inpatientID;
    }

    public void setInpatientID(String inpatientID) {
        this.inpatientID = inpatientID;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {

        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HsptPlan getPlan() {
        return plan;
    }

    public void setPlan(HsptPlan plan) {
        this.plan = plan;
    }

    public HsptPatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(HsptPatientType patientType) {
        this.patientType = patientType;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HsptUser getDoctor() {
        return doctor;
    }

    public void setDoctor(HsptUser doctor) {
        this.doctor = doctor;
    }

    public HsptUser getAddnDoctor() {
        return addnDoctor;
    }

    public void setAddnDoctor(HsptUser addnDoctor) {
        this.addnDoctor = addnDoctor;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<HsptDeliveryInfo> getDeliveryInfos() {
        return deliveryInfos;
    }

    public void setDeliveryInfos(Set<HsptDeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
    }

    public Set<HsptRetrieveInfo> getRetrieveInfos() {
        return retrieveInfos;
    }

    public void setRetrieveInfos(Set<HsptRetrieveInfo> retrieveInfos) {
        this.retrieveInfos = retrieveInfos;
    }


    public HsptPatient() {
    }

    public HsptPatient(String name, String pwd, String phone, Integer oldPatient,
                   HsptPatientType patientType, String email, HsptUser doctor, HsptUser addnDoctor,
                   String openID, Date createTime, Integer sex, HsptPlan plan) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.oldPatient = oldPatient;
        this.patientType = patientType;
        this.email = email;
        this.doctor = doctor;
        this.addnDoctor = addnDoctor;
        this.openID = openID;
        this.createTime = createTime;
        this.sex = sex;
        this.plan = plan;
    }


}
