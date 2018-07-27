package org.hspt.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqRoleUser;
import org.hspt.service.RoleService;

/**
 * <b> 系统角色用户授权 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@RequestMapping("/sys/role/user")
@RestController
@Api(tags = "用户中心系统角色用户授权接口", description = "提供对系统角色用户授权的管理")
@AuthRoles(roles = {BaseConstants.SYS_ADMIN_NAME, BaseConstants.DEV_USER_NAME, BaseConstants.OPER_USER_NAME})
public class SysRoleUserController {


    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色用户授权", notes = "角色用户授权，系统管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse addRoleUser(@RequestBody @Validated ReqRoleUser roleUser) throws BaseException {
        return roleService.addRoleUser(roleUser);
    }
}
