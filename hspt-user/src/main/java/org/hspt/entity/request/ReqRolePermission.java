package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hspt.base.BaseRequest;
import org.hspt.entity.dto.RolePermissionDTO;

import java.util.List;

/**
 * <b> 角色授权信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("角色授权请求实体信息")
public class ReqRolePermission extends BaseRequest {


    @ApiModelProperty("角色主键")
    Long pkRole;

    @ApiModelProperty("角色代码")
    String roleCode;

    @ApiModelProperty("角色名称")
    String roleName;

    @ApiModelProperty("角色描述")
    String roleInfo;

    @ApiModelProperty("角色对应的权限")
    List<Long> permissions;


}
