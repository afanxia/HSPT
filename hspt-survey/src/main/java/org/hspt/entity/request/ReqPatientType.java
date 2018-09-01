package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.base.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * <b> 病人类型信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("病人类型请求实体信息")
public class ReqPatientType extends BaseRequest {

    @ApiModelProperty("病人类型编号")
    @NotNull
    private Integer patientTypeId;    //病人类型编号

    @NotBlank
    @ApiModelProperty("病人类型名称")
    private String patientTypeName;    //病人类型名称

}
