package org.hspt.service.impl;

import com.querydsl.core.types.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.dao.jpa.EmployeesDAO;
import org.hspt.dao.jpa.UserDAO;
import org.hspt.dao.jpa.UserEmployeesDAO;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.ReqUserEmployees;
import org.hspt.entity.response.ResUser;
import org.hspt.entity.response.ResUserEmpInfo;
import org.hspt.service.UserEmployeesRelaService;

import java.util.List;

/**
 * <b> UserEmployeesRelaServiceHsptl </b>
 * <p>
 * 功能描述:用户员工关系服务实现体
 * </p>
 */
@Service
public class UserEmployeesRelaServiceImpl extends BaseService implements UserEmployeesRelaService {
    @Autowired
    private UserEmployeesDAO userEmpDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmployeesDAO empDAO;

    @Override
    public BaseResponse userBindEmp(ReqUserEmployees reqUserEmployees) throws BaseException {
        HsptEmployees emp = empDAO.findByPkEmployeesAndDr(reqUserEmployees.getPkEmployees(), BaseConstants.DATA_STATUS_OK);
        if (null == emp) {
            throw new BaseException(StatusCode.DATA_NOT_FOUND, "员工信息不存在");
        }
        HsptUser user = userDAO.findByPkUserAndDr(reqUserEmployees.getPkUser(), BaseConstants.DATA_STATUS_OK);
        if (null == user) {
            throw new BaseException(StatusCode.DATA_NOT_FOUND, "用户信息不存在");
        }
        HsptUserEmployees userEmp = userEmpDAO.findByPkEmployeesAndDr(reqUserEmployees.getPkEmployees(), BaseConstants.DATA_STATUS_OK);
        if (null != userEmp) {
            throw new BaseException(StatusCode.DATA_QUOTE, "员工已绑定其他用户,请先进行解绑");
        }
        //删除用户绑定所有员工
        QHsptUserEmployees qUserEmp = QHsptUserEmployees.hsptUserEmployees;
        getQueryFactory().update(qUserEmp).set(qUserEmp.dr, BaseConstants.DATA_STATUS_DEL).where(
                qUserEmp.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qUserEmp.pkUser.eq(reqUserEmployees.getPkUser()))
        ).execute();
        //保存员工雇员关系
        userEmp = new HsptUserEmployees();
        BeanUtils.copyProperties(reqUserEmployees, userEmp);
        userEmp = userEmpDAO.save(userEmp);
        //封装响应信息
        ResUserEmpInfo res = new ResUserEmpInfo();
        BeanUtils.copyProperties(userEmp, res);
        BeanUtils.copyProperties(user, res);
        BeanUtils.copyProperties(emp, res);
        return new BaseResponse(StatusCode.UPDATE_SUCCESS, "绑定成功");
    }

    @Override
    public BaseResponse userDebindEmp(long pkEmployees) throws BaseException {
        QHsptUserEmployees qUserEmp = QHsptUserEmployees.hsptUserEmployees;
        getQueryFactory().update(qUserEmp).set(qUserEmp.dr, BaseConstants.DATA_STATUS_DEL).where(
                qUserEmp.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qUserEmp.pkEmployees.eq(pkEmployees))
        ).execute();
        return new BaseResponse(StatusCode.UPDATE_SUCCESS, "解绑成功");
    }

    @Override
    public BaseResponse getUserEmpInfo(long pkEmployees) throws BaseException {
        HsptUserEmployees userEmp = userEmpDAO.findByPkEmployeesAndDr(pkEmployees, BaseConstants.DATA_STATUS_OK);
        ResUserEmpInfo res = new ResUserEmpInfo();
        if (null != userEmp) {
            BeanUtils.copyProperties(userEmp, res);
            //获取用户信息
            HsptUser user = userDAO.findByPkUserAndDr(userEmp.getPkUser(), BaseConstants.DATA_STATUS_OK);
            BeanUtils.copyProperties(user, res);
            //获取员工信息
            HsptEmployees emp = empDAO.findByPkEmployeesAndDr(userEmp.getPkEmployees(), BaseConstants.DATA_STATUS_OK);
            BeanUtils.copyProperties(emp, res);
        }
        return new BaseResponse(res);
    }

    @Override
    public BaseResponse getUserList(long pkGroup) throws BaseException {
        QHsptUser qUser = QHsptUser.hsptUser;
        List list = getQueryFactory().select(Projections.bean(
                ResUser.class,
                qUser.pkUser,
                qUser.userName,
                qUser.userCode
        )).from(qUser).where(
                qUser.pkGroup.eq(pkGroup)
                        .and(qUser.dr.eq(BaseConstants.DATA_STATUS_OK)
                                .and(qUser.userStatus.eq(0)))
        ).fetch();
        return new BaseResponse(list);
    }
}