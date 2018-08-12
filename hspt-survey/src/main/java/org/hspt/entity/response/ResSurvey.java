package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <b> 问卷基础信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("问卷响应实体信息")
public class ResSurvey implements Serializable {

    @ApiModelProperty("问卷编号")
    @NotNull
    private long pkSurvey;    //问卷编号

    @ApiModelProperty("问卷类型")
    @NotNull
    private long pkSurveyType;    //问卷类型

    @NotBlank
    @ApiModelProperty("问卷名称")
    private String surveyName;    //问卷名称

    @ApiModelProperty("科室")
    private String department;    //科室

    @ApiModelProperty("简介")
    private String description;    //简介

    @ApiModelProperty("随访频率")
    @NotNull
    private long frequency;    //随访频率

    @ApiModelProperty("是否注册时发送")
    private boolean sendOnRegister;
}
