package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b> 问卷类型 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("问卷类型响应实体信息")
public class ResSurveyType implements Serializable {

    @ApiModelProperty("问卷类型编号")
    @NotNull
    private Integer pkSurveyType;

    @NotBlank
    @ApiModelProperty("问卷类型名称")
    private String surveyTypeName;

    public ResSurveyType() {
        super();
    }
}
