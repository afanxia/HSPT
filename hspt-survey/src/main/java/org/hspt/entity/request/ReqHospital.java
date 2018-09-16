package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.base.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * <b> 医院信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("医院请求实体信息")
public class ReqHospital extends BaseRequest {

    @ApiModelProperty("医院编号")
    @NotNull
    private Integer hospitalId;

    @NotBlank
    @ApiModelProperty("医院名称")
    private String name;

    @ApiModelProperty("城市编号")
    @NotNull
    private Integer cityId;
}
