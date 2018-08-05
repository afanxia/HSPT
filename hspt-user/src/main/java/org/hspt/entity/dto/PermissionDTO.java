package org.hspt.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("获取role权限信息")
public class PermissionDTO {

    @ApiModelProperty("权限主键")
    private Long pkPermissions;

    @ApiModelProperty("权限编码")
    private String permissionsCode;

    @ApiModelProperty("权限名称")
    private String permissionsName;

    @ApiModelProperty("删除位标识")
    private Integer dr;

}
