package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b> 医院 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("医院响应实体信息")
public class ResHospital implements Serializable {

    @ApiModelProperty("医院编号")
    @NotNull
    private Integer hospitalId;

    @NotBlank
    @ApiModelProperty("医院名称")
    private String name;

    @ApiModelProperty("城市编号")
    @NotNull
    private Integer cityId;

    public ResHospital() {
        super();
    }
}
