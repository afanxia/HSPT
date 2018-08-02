package org.hspt.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hspt.entity.jpa.HsptPermissions;

import java.io.Serializable;
import java.util.List;

/**
 * <b> 用户菜单权限对应信息 </b>
 * <p>
 * 功能描述:存储用户的菜单权限对应信息，用于role-menu-permission中获取某一role的菜单和权限，权限中的dr位用于表明是否删除
 * </p>
 */
@Data
@ApiModel("用户菜单对应的权限信息")
public class MenuPermissionsDTO implements Serializable {

    @ApiModelProperty("菜单编码")
    private String menuCode;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单类型")
    private Integer menuType;
    @ApiModelProperty("菜单地址")
    private String menuUrl;

    @ApiModelProperty("菜单级别")
    private Integer menuLev;
    @ApiModelProperty("是否末级菜单，只有末级菜单才是具体节点")
    private String isEnd;
    @ApiModelProperty("菜单主键")
    private Long pkMenu;
    @ApiModelProperty("上级菜单主键")
    private Long pkFMenu;

    @ApiModelProperty("菜单拥有的权限")
    private List<HsptPermissions> permissions;

    public MenuPermissionsDTO() {
        super();
    }
}
