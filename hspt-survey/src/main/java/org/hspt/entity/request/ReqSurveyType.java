package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.base.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * <b> 问卷类型信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("问卷类型请求实体信息")
public class ReqSurveyType extends BaseRequest {

    @ApiModelProperty("问卷类型编号")
    @NotNull
    private long pkSurveyType;

    @NotBlank
    @ApiModelProperty("问卷类型名称")
    private String surveyTypeName;

}
