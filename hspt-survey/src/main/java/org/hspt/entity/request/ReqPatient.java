package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.base.BaseRequest;
import org.hspt.entity.dto.DeliveryInfoDTO;
import org.hspt.entity.dto.RetrieveInfoDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * <b> 病人信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("病人请求实体信息")
public class ReqPatient extends BaseRequest {

    @ApiModelProperty("病人编号")
    @NotNull
    private Integer patientId;    //病人编号

    @ApiModelProperty("病人类型")
    @NotNull
    private Integer patientTypeId;    //病人类型

    @NotBlank
    @ApiModelProperty("病人姓名")
    private String name;    //病人姓名

    @ApiModelProperty("密码")
    private String pwd;    //密码

    @ApiModelProperty("微信appID")
    private String appID; //微信appID

    @ApiModelProperty("微信openID")
    private String openID; //微信openID

    @ApiModelProperty("uniqID")
    private String uniqID;

    @ApiModelProperty("outpatientID")
    private String outpatientID;

    @ApiModelProperty("inpatientID")
    private String inpatientID;

    @ApiModelProperty("phone")
    private String phone;  //联系方式

    @ApiModelProperty("随访计划")
    @NotNull
    private Integer planId;    //随访计划

    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("直属医生")
    @NotNull
    private Long aid;    //直属医生

    @ApiModelProperty("共享医生")
    @NotNull
    private Long addnDoctorId;    //共享医生

    @ApiModelProperty("性别")
    @NotNull
    private Integer sex;    // male == 1, female == 0; different from sex in Plan

    @ApiModelProperty("oldPatient")
    @NotNull
    private Integer oldPatient;   // 1 新病例, 2 既往病例, 3 哮喘无忧用户

    @ApiModelProperty("state")
    @NotNull
    private Integer state; // 状态， -1为未激活（出现在回收站且不发送问卷），

    @ApiModelProperty("isReceivePicUploadSurvey")
    @NotNull
    private boolean isReceivePicUploadSurvey; // 是否接收病历上传的问卷

    @ApiModelProperty("birthday")
    @NotNull
    private Date birthday;

    @ApiModelProperty("createTime")
    @NotNull
    private Date createTime;    //创建时间

    @ApiModelProperty("deliveryInfos")
    private Set<DeliveryInfoDTO> deliveryInfos;    //该病人的随访问卷分发信息

    @ApiModelProperty("retrieveInfos")
    private Set<RetrieveInfoDTO> retrieveInfos;    //该病人的答卷信息
}
