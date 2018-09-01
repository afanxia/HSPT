package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b> 病人类型基础信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("病人类型响应实体信息")
public class ResPatientType implements Serializable {

    @ApiModelProperty("病人类型编号")
    @NotNull
    private Integer patientTypeId;    //病人类型编号

    @NotBlank
    @ApiModelProperty("病人类型名称")
    private String patientTypeName;    //病人类型名称
}
