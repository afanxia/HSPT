package org.hspt.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hspt.base.AuthRoles;
import org.hspt.base.BaseConstants;
import org.hspt.base.BaseException;
import org.hspt.base.BaseResponse;
import org.hspt.entity.request.ReqUserEmployees;
import org.hspt.entity.response.ResUser;
import org.hspt.entity.response.ResUserEmpInfo;
import org.hspt.service.UserEmployeesRelaService;

/**
 * <b> SysUserEmployeesRelaController </b>
 * <p>
 * 功能描述:SysUserEmployeesRelaController接口服务
 * </p>
 */
@RestController
@RequestMapping("/userEmployees")
@Api(tags = "用户中心用户员工关系接口", description = "用户员工关系接口，提供信息的获取与维护")
@AuthRoles(roles = {BaseConstants.SYS_ADMIN_NAME, BaseConstants.DEV_USER_NAME, BaseConstants.OPER_USER_NAME})
public class SysUserEmployeesRelaController {

    @Autowired
    private UserEmployeesRelaService service;

    @ApiOperation(value = "获取用户员工信息", notes = "获取用户员工信息，组织管理员默认可以访问")
    @RequestMapping(value = "/{employees_id}", method = RequestMethod.GET)
    public BaseResponse<ResUserEmpInfo> getUserEmpInfo(@PathVariable("employees_id") long pkEmployees) throws BaseException {

        return service.getUserEmpInfo(pkEmployees);
    }

    @ApiOperation(value = "获取组织用户列表信息", notes = "获取组织用户列表信息，组织管理员默认可以访问")
    @RequestMapping(value = "/userList/{group_id}", method = RequestMethod.GET)
    public BaseResponse<ResUserEmpInfo> getUserList(@PathVariable("group_id") long pkGroup) throws BaseException {
        return service.getUserList(pkGroup);
    }

    @ApiOperation(value = "用户绑定员工", notes = "用户绑定员工，组织管理员默认可以访问")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public BaseResponse<ResUser> userBindEmp(@RequestBody @Validated ReqUserEmployees reqUserEmployees) throws BaseException {
        return service.userBindEmp(reqUserEmployees);
    }

    @ApiOperation(value = "用户解绑员工", notes = "用户解绑员工，组织管理员默认可以访问")
    @RequestMapping(value = "/{employees_id}", method = RequestMethod.PUT)
    public BaseResponse<ResUserEmpInfo> userDebindEmp(@PathVariable("employees_id") long employeesId) throws BaseException {
        return service.userDebindEmp(employeesId);
    }
}
