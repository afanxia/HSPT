package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hspt.entity.dto.MenuPermissionsDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <b> 角色菜单信息 </b>
 * <p>
 * 功能描述: 用于获取某一role的菜单和权限, 返回的是全连接，靠dr位判断状态, 菜单仅包含叶节点
 * </p>
 */
@Data
@ApiModel("角色的菜单和权限信息")
public class ResRoleMenus implements Serializable {

    @ApiModelProperty("角色主键")
    private Long pkRole;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String roleInfo;

    @ApiModelProperty("该角色用户数")
    private Long numUsers;

    @ApiModelProperty("角色菜单和权限")
    private List<MenuPermissionsDTO> menuPermissions;

}
