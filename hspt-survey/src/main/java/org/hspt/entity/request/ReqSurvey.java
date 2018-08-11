package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.base.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * <b> 问卷信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("问卷请求实体信息")
public class ReqSurvey extends BaseRequest {

    @ApiModelProperty("问卷编号")
    @NotNull
    private Integer pkSurvey;    //问卷编号

    @ApiModelProperty("问卷类型")
    @NotNull
    private Integer pkSurveyType;    //问卷类型

    @NotBlank
    @ApiModelProperty("问卷名称")
    private String surveyName;    //问卷名称

    @ApiModelProperty("科室")
    private String department;    //科室

    @ApiModelProperty("简介")
    private String description;    //简介

    @ApiModelProperty("随访频率")
    @NotNull
    private Integer frequency;    //随访频率

    @NotBlank
    @ApiModelProperty("是否注册时发送")
    private boolean sendOnRegister;
}
