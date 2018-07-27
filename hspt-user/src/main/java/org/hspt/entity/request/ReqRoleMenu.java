package org.hspt.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hspt.base.BaseRequest;
import org.hspt.entity.dto.RoleMenuDTO;

import java.util.List;

/**
 * <b> 角色菜单授权信息 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Data
@ApiModel("角色菜单授权请求实体信息")
public class ReqRoleMenu extends BaseRequest {


    @ApiModelProperty("角色菜单对照")
    List<RoleMenuDTO> roleMenus;


}
