package org.hspt.entity.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hspt.entity.jpa.HsptPermissions;

import java.io.Serializable;
import java.util.List;

/**
 * <b> 用户初始信息 </b>
 * <p>
 * 功能描述: 用户登陆后第一次加载的信息，包含必要的基本信息和菜单与权限信息
 * </p>
 */
@Data
@ApiModel("用户初始信息")
public class UserInfoDTO implements Serializable {

    @ApiModelProperty("用户编号")
    private long userId;
    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("角色编号")
    private long roleId;
    @ApiModelProperty("角色编码")
    private String roleCode;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色类型")
    private long roleType;

    @ApiModelProperty("菜单列表")
    private List<UserMenuDTO> menuList;

    @ApiModelProperty("权限列表")
    private List<HsptPermissions> permissionList;

    public UserInfoDTO() {
        super();
    }
}
