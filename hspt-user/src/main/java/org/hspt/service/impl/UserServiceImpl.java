package org.hspt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.hspt.base.*;
import org.hspt.common.pub.Pub_AuthUtils;
import org.hspt.common.pub.Pub_RedisUtils;
import org.hspt.common.pub.Pub_Tools;
import org.hspt.dao.jpa.*;
import org.hspt.dao.mybatis.PubMapper;
import org.hspt.entity.dto.UserInfoDTO;
import org.hspt.entity.dto.UserMenuDTO;
import org.hspt.entity.dto.UserResourceDTO;
import org.hspt.entity.jpa.*;
import org.hspt.entity.request.*;
import org.hspt.entity.response.ResCount;
import org.hspt.entity.response.ResUser;
import org.hspt.service.UserService;
import org.hspt.utils.DateUtils;
import org.hspt.utils.MD5Util;
import org.hspt.utils.RandomStrUtils;
import org.hspt.utils.SensitiveInfoUtils;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

/**
 * <b> 提供基于用户的相关基本操作 </b>
 * <p>
 * 功能描述:
 * </p>
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    /**
     * 菜单编号长度
     */
    public static final int MENU_LEV = 3;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private PermissionsDAO permissionsDAO;

    @Autowired
    private UserEmployeesDAO impUserEmployeesDAO;

    @Autowired
    private EmployeesDAO impEmployeesDAO;

    @Autowired
    private OrgDAO impOrgDAO;

    @Autowired
    private PubMapper pubMapper;

    @Autowired
    private Pub_Tools pubTools;

    @Autowired
    private Pub_AuthUtils authUtils;

    @Autowired
    private Pub_RedisUtils redisUtils;


    /**
     * 生成密码
     *
     * @param passWord 明文密码
     * @param random   随机码
     * @return
     */
    public static String genPassWord(String passWord, String random) {
        String pdOne = MD5Util.MD5(passWord);
        return MD5Util.MD5(pdOne + random);
    }

    @Override
    public BaseResponse registerAdmin(RegisterUser regUser) throws BaseException {
        //构造默认用户
        HsptUser impUser = new HsptUser();
        BeanUtils.copyProperties(regUser, impUser);
        impUser.setPkGroup(-1L);
        return saveUser(impUser, BaseConstants.SYS_ADMIN_TYPE, -1);
    }

    @Override
    public BaseResponse getUserInfo(long userId) throws BaseException {
        HsptUser user = userDAO.findByPkUserAndDr(userId, BaseConstants.DATA_STATUS_OK);
        List<UserMenuDTO> menus = new ArrayList<>();
        //List<UserResourceDTO> permissions = new ArrayList<>();
        List<HsptPermissions> permissions = new ArrayList<>();

        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setUserName(user.getUserName());
        userInfo.setUserId(user.getPkUser());
        HsptUserRole userRole = userRoleDAO.findByPkUserAndDr(user.getPkUser(), BaseConstants.DATA_STATUS_OK);
        HsptRole role = roleDAO.findByPkRoleAndDr(userRole.getPkRole(), BaseConstants.DATA_STATUS_OK);
        userInfo.setRoleId(role.getPkRole());
        userInfo.setRoleCode(role.getRoleCode());
        userInfo.setRoleName(role.getRoleName());
        userInfo.setRoleType(role.getRoleType());

        if("SYS_ADMIN".equals(role.getRoleCode())) { // admin will have all menus/permissions
            menus = getUserMenus();
            //permissions = getUserResources();
            permissions = permissionsDAO.findAll();
        }
        else {
            menus = getUserMenus(userId);
            permissions = getPermissions(userId);
        }
        menus = generateMenuTree(menus);
        userInfo.setMenuList(menus);
        userInfo.setPermissionList(permissions);
        return new BaseResponse(userInfo);
    }


    public List<UserMenuDTO> generateMenuTree(List<UserMenuDTO> rootMenu) {
        List<UserMenuDTO> menuList = new ArrayList<UserMenuDTO>();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单
            if (rootMenu.get(i).getMenuLev() == 1) {
                menuList.add(rootMenu.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (UserMenuDTO menu : menuList) {
            menu.setChildren(getChild(menu.getPkMenu(), rootMenu));
        }
        return menuList;
    }


    /**
     * 递归查找子菜单
     *
     * @param pkMenu
     *            当前菜单pkMenu
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<UserMenuDTO> getChild(long pkMenu, List<UserMenuDTO> rootMenu) {
        // 子菜单
        List<UserMenuDTO> childList = new ArrayList<>();
        for (UserMenuDTO menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getPkFMenu() == pkMenu) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (UserMenuDTO menu : childList) {// 没有url子菜单还有子菜单
            if ( !("Y".equals(menu.getIsEnd())) ) {
                // 递归
                menu.setChildren(getChild(menu.getPkMenu(), rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    @Override
    public BaseResponse registerGroupAdmin(RegisterUser regUser, long groupId) throws BaseException {
        //校验操作的组织是否是自己的组织
        if (-1 != getPkGroup()) {
            checkGroupIsMy(groupId);
        }

        //构造默认用户
        HsptUser impUser = new HsptUser();
        BeanUtils.copyProperties(regUser, impUser);
        if (null == groupDAO.findOne(groupId)) {
            throw new BaseException(StatusCode.GROUP_NOT_FOUND);
        }
        impUser.setPkGroup(groupId);
        return saveUser(impUser, BaseConstants.GROUP_ADMIN_TYPE, -1);
    }

    @Override
    public BaseResponse registerRoleUser(RegisterUser regUser, long pkRole) throws BaseException {
        //构造默认用户
        HsptUser impUser = new HsptUser();
        BeanUtils.copyProperties(regUser, impUser);
        impUser.setPkGroup(getPkGroup());
        return saveUser(impUser, BaseConstants.GEN_USER_TYPE, pkRole);
    }

    @Override
    public BaseResponse updateRoleUser(UpdateUser updateUser, long pkRole) throws BaseException {
        HsptUser impUser = userDAO.findByUserCodeAndDr(updateUser.getUserCode(), BaseConstants.DATA_STATUS_OK);
        BeanUtils.copyProperties(updateUser, impUser);
        userDAO.save(impUser);
        return updateUser(impUser, BaseConstants.GEN_USER_TYPE, pkRole);
    }

    @Override
    public BaseResponse registerGroupUser(RegisterUser regUser, long groupId, long pkRole) throws BaseException {
        //校验操作的组织是否是自己的组织
        checkGroupIsMy(groupId);
        //构造默认用户
        HsptUser impUser = new HsptUser();
        BeanUtils.copyProperties(regUser, impUser);
        impUser.setPkGroup(groupId);
        if (null == groupDAO.findOne(groupId)) {
            throw new BaseException(StatusCode.GROUP_NOT_FOUND);
        }
        return saveUser(impUser, BaseConstants.GEN_USER_TYPE, pkRole);
    }

    @Override
    public BaseResponse register(RegisterUser regUser) throws BaseException {
        //构造默认用户
        HsptUser impUser = new HsptUser();
        BeanUtils.copyProperties(regUser, impUser);
        return saveUser(impUser, BaseConstants.PUB_USER_TYPE, -1);
    }

    @Override
    public BaseResponse login(LoginUser loginUser) throws BaseException {
        String usercode = loginUser.getUserCode();
        if (null == usercode) {
            throw new BaseException(StatusCode.USERNAME_NOT_FOUND);
        }


        String rawPassword = loginUser.getUserPassword();
        if (null == rawPassword) {
            throw new BaseException(StatusCode.PASSWORD_NOT_FOUND);
        }


        //使用DSL语法
        QHsptUser qHsptUser = QHsptUser.hsptUser;

        HsptUser user = getQueryFactory().
                selectFrom(qHsptUser).
                //允许使用账号登陆，暂时去除手机号、邮箱登陆方式
                        where(qHsptUser.userCode.eq(usercode))
                .fetchOne();


        //数据不存在或者数据删除
        if (null == user || BaseConstants.DATA_STATUS_DEL == user.getDr()) {
            throw new BaseException(StatusCode.ACCOUNT_NOT_FOUND);
        }

        //判断账号是否超过停用时间
        if (StrUtil.isNotBlank(user.getEndTime())) {
            long num = DateUtils.getSecondDiffer(DateUtils.getCurrentDateTime(), user.getEndTime());
            if (0 < num) {
                throw new BaseException(StatusCode.ACCOUNT_EXPIRED);
            }
        }


        //数据封存
        if (BaseConstants.DATA_STATUS_CLOSE == user.getDr()) {
            throw new BaseException(StatusCode.ACCOUNT_DISABLED);
        }

        //账户锁定
        if (1 == user.getUserStatus()) {
            throw new BaseException(StatusCode.ACCOUNT_LOCKED);
        }

        //验证密码
        if (!user.getUserPassword().equals(genPassWord(loginUser.getUserPassword(), user.getEnKey()))) {
            throw new BaseException(StatusCode.PASSWORD_ERROR);
        }


        //刷新用户的权限信息
        authUtils.reloadByPkUser(user.getPkUser());

        return new BaseResponse(setBaseTokenByHsptUser(user));
    }


    /**
     * 根据用户数据初始化登陆用户信息
     *
     * @param user
     * @return
     */
    private BaseToken setBaseTokenByHsptUser(HsptUser user) {
        BaseToken token = new BaseToken();
        token.setUserId(user.getPkUser());
        token.setUserName(user.getUserCode());
        token.setPkGroup(user.getPkGroup() == null ? -1 : user.getPkGroup());
        token.setUserRole(getUserRoles(user.getPkUser()));
        token.setEndTime(user.getEndTime());
        token.setFullName(user.getUserName());


        //组织用户获取当前人员及机构
        if (-1 != token.getPkGroup()) {
            HsptUserEmployees iue = impUserEmployeesDAO.findByPkUserAndDr(user.getPkUser(), BaseConstants.DATA_STATUS_OK);
            if (null != iue) {
                //设置当前用户关联的员工
                token.setPkEmp(iue.getPkEmployees());
                //设置员工管理的机构
                HsptEmployees impEmployees = impEmployeesDAO.findOne(iue.getPkEmployees());
                //设置机构主键
                token.setPkOrg(impEmployees.getPkOrganization());

                //设置员工名称
                token.setEmpName(impEmployees.getEmployeesName());

                //机构名称
                token.setOrgName(impOrgDAO.findOne(impEmployees.getPkOrganization()).getOrgName());

                //设置用户的职务信息
                QHsptEmployeesDuties qed = QHsptEmployeesDuties.hsptEmployeesDuties;
                QHsptDuties duties = QHsptDuties.hsptDuties;
                List<HsptDuties> duList = getQueryFactory().select(duties)
                        .from(qed, duties)
                        .where(qed.pkEmployees.eq(impEmployees.getPkEmployees())
                                .and(qed.pkDuties.eq(duties.pkDuties))
                                .and(qed.dr.eq(BaseConstants.DATA_STATUS_OK))).fetch();

                String dustName = "";
                if (null != duList) {
                    if (0 < duList.size()) {
                        for (HsptDuties d : duList) {
                            dustName += d.getDutiesName() + ",";
                        }
                        dustName = dustName.substring(0, dustName.length() - 1);
                    }
                }
                token.setDutiesName(dustName);

            }
        }

        return token;

    }

    @Override
    public BaseResponse sso(ReqOtherLogin otherLogin) {


        //使用DSL语法
        QHsptUser qHsptUser = QHsptUser.hsptUser;

        //通过雇员的ID进行查询
        QHsptUserEmployees qHsptUserEmployees = QHsptUserEmployees.hsptUserEmployees;
        QHsptEmployees qHsptEmployees = QHsptEmployees.hsptEmployees;
        HsptUserEmployees employees = getQueryFactory().
                select(qHsptUserEmployees).from(qHsptUserEmployees, qHsptEmployees)
                .where(qHsptEmployees.pkEmployees.eq(qHsptUserEmployees.pkEmployees)
                        .and(qHsptUserEmployees.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(qHsptEmployees.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(qHsptEmployees.employeesCode.eq(otherLogin.getUserKey())).and(qHsptEmployees.pkGroup.eq(getPkGroup())))
                .fetchOne();

        if (null == employees) {
            throw new BaseException(StatusCode.NOT_FOUND);
        }


        HsptUser user = getQueryFactory().
                selectFrom(qHsptUser).
                //允许使用账号、邮箱、手机号登陆
                        where(qHsptUser.pkUser.eq(employees.getPkUser()))
                .fetchOne();

        //判断账号是否超过停用时间
        if (StrUtil.isNotBlank(user.getEndTime())) {
            long num = DateUtils.getSecondDiffer(DateUtils.getCurrentDateTime(), user.getEndTime());
            if (0 < num) {
                throw new BaseException(StatusCode.ACCOUNT_EXPIRED);
            }
        }


        //判断请求的TOKEN是否与数据查询到的人员属于相同公司
        if (!user.getPkGroup().equals(getPkGroup())) {
            throw new BaseException(StatusCode.UNAUTHORIZED);
        }

        //数据不存在或者数据删除
        if (null == user || BaseConstants.DATA_STATUS_DEL == user.getDr()) {
            throw new BaseException(StatusCode.ACCOUNT_NOT_FOUND);
        }

        //数据封存
        if (BaseConstants.DATA_STATUS_CLOSE == user.getDr()) {
            throw new BaseException(StatusCode.ACCOUNT_DISABLED);
        }

        //账户锁定
        if (1 == user.getUserStatus()) {
            throw new BaseException(StatusCode.ACCOUNT_LOCKED);
        }

        //刷新用户的权限信息
        authUtils.reloadByPkUser(user.getPkUser());


        return new BaseResponse(setBaseTokenByHsptUser(user));
    }

    @Override
    public BaseResponse password(long userId, ReqPassWord passWord) throws BaseException {

        QHsptUser qHsptUser = QHsptUser.hsptUser;

        HsptUser user = userDAO.findOne(qHsptUser.pkUser.eq(userId).and(qHsptUser.dr.eq(BaseConstants.DATA_STATUS_OK)));

        if (null != user) {
            //获取加密KEY
            String random = user.getEnKey();
            String oldPass = genPassWord(passWord.getOldpassword(), random);

            if (!user.getUserPassword().equals(oldPass)) {
                throw new BaseException(StatusCode.PASSWORD_ERROR);
            }

            //设置变更后的密码
            user.setUserPassword(genPassWord(passWord.getPassword(), random));
            //设置最后修改时间
            user.setLastPasswordResetDate(DateUtils.getTimestamp());

            //执行更新
            userDAO.save(user);

            //将所有的TOKEN清理掉,所有之前登陆的用户都需要用新密码进行登陆
            getRedisUtils().cleanAllToken(userId);

            return new BaseResponse(StatusCode.UPDATE_SUCCESS);
        }


        return new BaseResponse(StatusCode.UPDATE_SUCCESS);
    }


    @Override
    public BaseResponse setUserBase(long userId, ReqUserBase userBase) throws BaseException {

        HsptUser impUser = userDAO.findOne(userId);
        if (null == impUser) {
            throw new BaseException(StatusCode.ACCOUNT_NOT_FOUND);
        }

        //非系统管理员判断是否操作用户和登陆人是同一个用户
        if (-1L != getPkGroup()) {
            checkGroupIsMy(impUser.getPkGroup());
        }


        impUser.setUserStatus(userBase.getUserStatus());
        impUser.setUserName(userBase.getUserName());
        impUser.setEndTime(userBase.getEndTime());

        //更新
        userDAO.save(impUser);

        //将所有的TOKEN清理掉,所有之前登陆的用户都需要进行登陆
        getRedisUtils().cleanAllToken(userId);

        return new BaseResponse(StatusCode.UPDATE_SUCCESS);
    }


    @Override
    public BaseResponse getUserCount(Predicate user, Predicate role, Predicate group) throws BaseException {
        QHsptUser qHsptUser = QHsptUser.hsptUser;
        QHsptUserRole userRole = QHsptUserRole.hsptUserRole;
        QHsptRole qRole = QHsptRole.hsptRole;
        QHsptGroup qGroup = QHsptGroup.hsptGroup;

        if (-1L == getPkGroup()) {
            long count = getQueryFactory().select(qHsptUser.pkUser.count()
            ).from(qHsptUser, userRole, qRole, qGroup)
                    .where(qHsptUser.dr.in(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptUser.pkUser.eq(userRole.pkUser))
                            .and(userRole.pkRole.eq(qRole.pkRole))
                            .and(qHsptUser.pkGroup.eq(qGroup.pkGroup))
                            .and(user).and(role).and(group))
                    .fetchOne();
            return new BaseResponse(new ResCount(count));
        }
        long count = getQueryFactory().select(qHsptUser.pkUser.count()
        ).from(qHsptUser, userRole, qRole, qGroup)
                .where(qHsptUser.dr.in(BaseConstants.DATA_STATUS_OK)
                        .and(qHsptUser.pkUser.eq(userRole.pkUser))
                        .and(userRole.pkRole.eq(qRole.pkRole))
                        .and(qHsptUser.pkGroup.eq(qGroup.pkGroup))
                        .and(user).and(role).and(group).and(qHsptUser.pkGroup.eq(getPkGroup())))
                .fetchOne();
        return new BaseResponse(new ResCount(count));
    }

    @Override
    public BaseResponse delUser(long userId) throws BaseException {
        QHsptUser qUser = QHsptUser.hsptUser;
        HsptUser user = userDAO.findOne(qUser.pkUser.eq(userId).and(qUser.dr.eq(BaseConstants.DATA_STATUS_OK)));
        if (null != user) {
            //锁定账号
            user.setDr(BaseConstants.DATA_STATUS_DEL);
            userDAO.save(user);

            //将所有的TOKEN清理掉,所有之前登陆的用户都需要用新密码进行登陆
            getRedisUtils().cleanAllToken(userId);
        }

        return new BaseResponse(StatusCode.DELETE_SUCCESS);
    }


    @Override
    public BaseResponse getUserList(Pageable pageable, boolean encryption, Predicate user, Predicate role, Predicate group) throws BaseException {
        QHsptUser qHsptUser = QHsptUser.hsptUser;
        QHsptUserRole userRole = QHsptUserRole.hsptUserRole;
        QHsptRole qRole = QHsptRole.hsptRole;
        QHsptGroup qGroup = QHsptGroup.hsptGroup;

        if (-1L == getPkGroup()) {
            List<ResUser> resUserList = getQueryFactory().select(Projections.bean(
                    ResUser.class,
                    qHsptUser.pkUser,
                    qHsptUser.userCode,
                    qHsptUser.userName,
                    qHsptUser.userEmail,
                    qHsptUser.userPhone,
                    qHsptUser.userId,
                    qHsptUser.userStatus,
                    qHsptUser.userAuth,
                    qHsptUser.initUser,
                    qHsptUser.endTime,
                    qRole.roleCode,
                    qRole.roleName,
                    qGroup.groupCode,
                    qGroup.groupName
            )).from(qHsptUser, userRole, qRole, qGroup)
                    .where(qHsptUser.dr.in(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptUser.pkUser.eq(userRole.pkUser))
                            .and(userRole.pkRole.eq(qRole.pkRole))
                            .and(qHsptUser.pkGroup.eq(qGroup.pkGroup))
                            .and(user).and(role).and(group))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            if (encryption) {
                try {
                    SensitiveInfoUtils.sensitiveObject(resUserList);
                } catch (IllegalAccessException e) {
                    throw new BaseException(StatusCode.EXCEPTION);
                }
            }

            return new BaseResponse(resUserList);
        }

        List<ResUser> resUserList = getQueryFactory().select(Projections.bean(
                ResUser.class,
                qHsptUser.pkUser,
                qHsptUser.userCode,
                qHsptUser.userName,
                qHsptUser.userEmail,
                qHsptUser.userPhone,
                qHsptUser.userId,
                qHsptUser.userStatus,
                qHsptUser.userAuth,
                qHsptUser.initUser,
                qHsptUser.endTime,
                qRole.roleCode,
                qRole.roleName,
                qGroup.groupCode,
                qGroup.groupName
        )).from(qHsptUser, userRole, qRole, qGroup)
                .where(qHsptUser.dr.in(BaseConstants.DATA_STATUS_OK)
                        .and(qHsptUser.pkUser.eq(userRole.pkUser))
                        .and(userRole.pkRole.eq(qRole.pkRole))
                        .and(qHsptUser.pkGroup.eq(qGroup.pkGroup))
                        .and(user).and(role).and(group).and(qHsptUser.pkGroup.eq(getPkGroup())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (encryption) {
            try {
                SensitiveInfoUtils.sensitiveObject(resUserList);
            } catch (IllegalAccessException e) {
                throw new BaseException(StatusCode.EXCEPTION);
            }
        }

        return new BaseResponse(resUserList);
    }

    /**
     * 生成用户信息
     *
     * @param impUser  注册提交内容
     * @param userType 用户类型
     * @param pkRole   角色主键
     * @param
     * @return
     */
    public BaseResponse saveUser(HsptUser impUser, int userType, long pkRole) {


        if (null != userDAO.findByUserCodeAndDr(impUser.getUserCode(), BaseConstants.DATA_STATUS_OK)) {
            throw new BaseException(StatusCode.ADD_ERROR_EXISTS);
        }

        if (StringUtils.isNotBlank(impUser.getUserEmail())) {
            if (null != userDAO.findByUserEmailAndDr(impUser.getUserEmail(), BaseConstants.DATA_STATUS_OK)) {
                throw new BaseException(StatusCode.EMAIL_ERROR_EXISTS);
            }
        }

        if (StringUtils.isNotBlank(impUser.getUserPhone())) {
            if (null != userDAO.findByUserPhoneAndDr(impUser.getUserPhone(), BaseConstants.DATA_STATUS_OK)) {
                throw new BaseException(StatusCode.PHONE_ERROR_EXISTS);
            }
        }


        String random = RandomStrUtils.generateRandomString(8);


        //进行密码加密
        impUser.setUserPassword(genPassWord(impUser.getUserPassword(), random));
        impUser.setLastPasswordResetDate(DateUtils.getTimestamp());
        impUser.setEnKey(random);
        //设置用户为初始用户，此处用于一些要求用户第一次登陆后需要一些个性操作使用
        impUser.setInitUser(0);
        impUser.setPkUser(pubTools.genUUID());
        //设置用户状态 0为正常 1为锁定
        impUser.setUserStatus(0);
        //默认为认证用户，只有开放注册进来的用户才为非注册用户
        impUser.setUserAuth(1);


        //设置默认用户角色
        HsptUserRole userRole = new HsptUserRole();


        HsptRole role = null;

        if (BaseConstants.SYS_ADMIN_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.SYS_ADMIN_NAME, BaseConstants.DATA_STATUS_OK);

        } else if (BaseConstants.GROUP_ADMIN_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.GROUP_ADMIN_NAME, BaseConstants.DATA_STATUS_OK);

        } else if (BaseConstants.PUB_USER_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.PUB_USER_NAME, BaseConstants.DATA_STATUS_OK);
            //开发注册用户 认证状态为0 如果需要认证则需要身份证等信息
            impUser.setUserAuth(0);
            //开发注册的用户默认放入开发组织中
            impUser.setPkGroup(groupDAO.findByGroupCodeAndDr("pub", BaseConstants.DATA_STATUS_OK).getPkGroup());
        } else if (BaseConstants.GEN_USER_TYPE == userType) {
            role = roleDAO.findOne(pkRole);
        }

        if (null == role) {
            throw new BaseException(StatusCode.ROLE_NOT_FOUND);
        }

        impUser = userDAO.save(impUser);

        userRole.setPkUser(impUser.getPkUser());
        userRole.setPkRole(role.getPkRole());
        userRole.setUserRoleId(pubTools.genUUID());
        userRoleDAO.save(userRole);

        //返回信息

        return new BaseResponse(StatusCode.ADD_SUCCESS, setBaseTokenByHsptUser(impUser));
    }

    /**
     * 更新用户信息
     *
     * @param impUser  注册提交内容
     * @param userType 用户类型
     * @param pkRole   角色主键
     * @param
     * @return
     */
    public BaseResponse updateUser(HsptUser impUser, int userType, long pkRole) {

        if (null == userDAO.findByUserCodeAndDr(impUser.getUserCode(), BaseConstants.DATA_STATUS_OK)) {
            throw new BaseException(StatusCode.USER_NOT_FOUND);
        }

        if (StringUtils.isNotBlank(impUser.getUserEmail())) {
            if (null != userDAO.findByUserEmailAndDr(impUser.getUserEmail(), BaseConstants.DATA_STATUS_OK)) {
                throw new BaseException(StatusCode.EMAIL_ERROR_EXISTS);
            }
        }

        if (StringUtils.isNotBlank(impUser.getUserPhone())) {
            if (null != userDAO.findByUserPhoneAndDr(impUser.getUserPhone(), BaseConstants.DATA_STATUS_OK)) {
                throw new BaseException(StatusCode.PHONE_ERROR_EXISTS);
            }
        }

        HsptRole role = null;
        if (BaseConstants.SYS_ADMIN_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.SYS_ADMIN_NAME, BaseConstants.DATA_STATUS_OK);
        } else if (BaseConstants.GROUP_ADMIN_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.GROUP_ADMIN_NAME, BaseConstants.DATA_STATUS_OK);
        } else if (BaseConstants.PUB_USER_TYPE == userType) {
            role = roleDAO.findByRoleCodeAndDr(BaseConstants.PUB_USER_NAME, BaseConstants.DATA_STATUS_OK);
            //开发注册用户 认证状态为0 如果需要认证则需要身份证等信息
            impUser.setUserAuth(0);
            //开发注册的用户默认放入开发组织中
            impUser.setPkGroup(groupDAO.findByGroupCodeAndDr("pub", BaseConstants.DATA_STATUS_OK).getPkGroup());
        } else if (BaseConstants.GEN_USER_TYPE == userType) {
            role = roleDAO.findOne(pkRole);
        }

        if (null == role) {
            throw new BaseException(StatusCode.ROLE_NOT_FOUND);
        }

        try {
            HsptUser user = userDAO.findByUserCodeAndDr(impUser.getUserCode(), BaseConstants.DATA_STATUS_OK);
            HsptUserRole userRole = userRoleDAO.findByPkUserAndDr(user.getPkUser(), BaseConstants.DATA_STATUS_OK);
            userRole.setPkRole(pkRole);
            userRoleDAO.save(userRole);
            return new BaseResponse(StatusCode.UPDATE_SUCCESS, setBaseTokenByHsptUser(impUser));
        }catch (Exception e) {
            throw new BaseException(StatusCode.UPDATE_ERROR);
        }

    }

    /**
     * 获取用户角色
     *
     * @param pkUser 用户主键
     * @return
     */
    private String getUserRoles(long pkUser) {
        //使用DSL语法进行数据查询
        QHsptRole qHsptRole = QHsptRole.hsptRole;
        QHsptUserRole qHsptUserRole = QHsptUserRole.hsptUserRole;
        List<HsptRole> roles = getQueryFactory()
                .select(qHsptRole)
                .from(qHsptRole, qHsptUserRole)
                .where(
                        qHsptUserRole.pkRole.eq(qHsptRole.pkRole)
                                .and(qHsptRole.dr.eq(0))
                                .and(qHsptUserRole.dr.eq(0))
                                .and(qHsptUserRole.pkUser.eq(pkUser)))

                .fetch();
        StringBuffer roleStr = new StringBuffer();
        if (null != roles) {
            for (HsptRole role : roles) {
                roleStr.append(role.getRoleCode()).append(",");
            }
            return roleStr.substring(0, roleStr.length() - 1).toString();
        }
        return roleStr.toString();
    }

    /**
     * 根据用户主键获取用户菜单
     *
     * @param pkUser 用户主键
     */
    public List<UserMenuDTO> getUserMenus(long pkUser) {

        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        QHsptRoleMenu qHsptRoleMenu = QHsptRoleMenu.hsptRoleMenu;
        QHsptUserRole qHsptUserRole = QHsptUserRole.hsptUserRole;

        //根据用户角色获取用户末级菜单
        List<UserMenuDTO> userMenuList = getQueryFactory().select(
                Projections.bean(
                        UserMenuDTO.class,
                        qHsptMenu.menuCode,
                        qHsptMenu.menuName,
                        qHsptMenu.menuType,
                        qHsptMenu.menuLev,
                        qHsptMenu.orderCode,
                        qHsptMenu.menuIco,
                        qHsptMenu.menuUrl,
                        qHsptMenu.isEnd,
                        qHsptMenu.pkMenu,
                        qHsptMenu.pkFMenu
                )
        ).from(qHsptMenu, qHsptRoleMenu, qHsptUserRole)
                .where(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(qHsptRoleMenu.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(qHsptUserRole.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(qHsptMenu.pkMenu.eq(qHsptRoleMenu.pkMenu))
                        .and(qHsptRoleMenu.pkRole.eq(qHsptUserRole.pkRole))
                        .and(qHsptUserRole.pkUser.eq(pkUser))
                )
                .groupBy(qHsptMenu.menuCode,
                        qHsptMenu.menuName,
                        qHsptMenu.menuType,
                        qHsptMenu.menuLev,
                        qHsptMenu.orderCode,
                        qHsptMenu.menuIco,
                        qHsptMenu.menuUrl,
                        qHsptMenu.isEnd,
                        qHsptMenu.pkMenu,
                        qHsptMenu.pkFMenu)
                .orderBy(qHsptMenu.orderCode.asc())
                .fetch();


        //暂时存储用户菜单内部编码，内部编码必须为一级三位，否则会出现问题，此处为了效率不推荐使用数据库递归方式，直接前台拿到所有节点的内部序号，一次性查询出来
        HashMap<String, String> menuCodeMap = new HashMap<>();
        for (UserMenuDTO userMenu : userMenuList) {
            String orderCode = userMenu.getOrderCode();
            if (orderCode.length() % 3 != 0) {
                throw new BaseException(StatusCode.MENU_OEDER_CODE_LENGTH_ERROR);
            }
            //拆分功能节点内部序号用于构建菜单
            for (int i = 0; i < orderCode.length() / MENU_LEV; i++) {
                String menuCode = orderCode.substring(0, (i + 1) * 3);
                if (!menuCodeMap.containsKey(menuCode)) {
                    menuCodeMap.put(menuCode, menuCode);
                }
            }
        }

        //获取用户全部菜单
        if (menuCodeMap.size() == 0) {
            userMenuList = null;
        } else {
            userMenuList = getQueryFactory().select(
                    Projections.bean(
                            UserMenuDTO.class,
                            qHsptMenu.menuCode,
                            qHsptMenu.menuName,
                            qHsptMenu.menuType,
                            qHsptMenu.menuLev,
                            qHsptMenu.orderCode,
                            qHsptMenu.menuIco,
                            qHsptMenu.menuUrl,
                            qHsptMenu.isEnd,
                            qHsptMenu.pkMenu,
                            qHsptMenu.pkFMenu
                    )
            ).from(qHsptMenu)
                    .where(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK)
                            .and(qHsptMenu.orderCode
                                    .in(menuCodeMap.keySet().toArray(new String[0]))))
                    .orderBy(qHsptMenu.orderCode.asc())
                    .fetch();
        }

        return userMenuList;
    }


    /**
     * 获取用户相关信息，后期登陆的时候 可以一起获取，如果效率低下后期可拆分部分功能为异步
     *
     * @param pkUser 用户主键
     * @return
     */
    public List<UserResourceDTO> getUserResources(long pkUser) {

        //接口资源信息
        QHsptResource res = QHsptResource.hsptResource;
        //权限信息
        QHsptPermissions ip = QHsptPermissions.hsptPermissions;
        //权限资源信息
        QHsptPermissionsResource ips = QHsptPermissionsResource.hsptPermissionsResource;
        //菜单权限信息
        QHsptMenuPermissions imp = QHsptMenuPermissions.hsptMenuPermissions;
        //菜单新
        QHsptMenu im = QHsptMenu.hsptMenu;
        //角色菜单信息
        QHsptRoleMenu irm = QHsptRoleMenu.hsptRoleMenu;
        //角色信息
        QHsptRole ir = QHsptRole.hsptRole;
        //用户角色
        QHsptUserRole iur = QHsptUserRole.hsptUserRole;
        //用户信息
        QHsptUser iu = QHsptUser.hsptUser;

        return getQueryFactory().select(Projections.bean(
                UserResourceDTO.class,
                res.methodName,
                res.methodPath,
                res.modulePath,
                res.moduleId,
                res.url,
                ip.permissionsCode,
                ip.permissionsName,
                im.menuCode,
                im.menuName,
                im.menuType,
                im.menuUrl,
                im.orderCode,
                ir.roleCode,
                ir.roleName,
                ir.roleType
        )).from(res, ips, ip, imp, im, irm, ir, iur, iu)
                .where(res.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(ip.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(ips.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(imp.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(im.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(irm.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(ir.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(iur.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(iu.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(res.pkResource.eq(ips.pkResource))
                        .and(ips.pkPermissions.eq(ip.pkPermissions))
                        .and(ip.pkPermissions.eq(imp.pkPermissions))
                        .and(im.pkMenu.eq(irm.pkMenu))
                        .and(imp.pkMenu.eq(im.pkMenu))
                        .and(irm.pkRole.eq(ir.pkRole))
                        .and(ir.pkRole.eq(iur.pkRole))
                        .and(iur.pkUser.eq(iu.pkUser))
                        .and(iu.pkUser.eq(pkUser)))
                //按照菜单内部排序号返回
                .orderBy(im.orderCode.asc())
                .fetch();
    }

    public List<UserMenuDTO> getUserMenus() throws BaseException {
        QHsptMenu qHsptMenu = QHsptMenu.hsptMenu;
        List list = getQueryFactory().select(Projections.bean(
                UserMenuDTO.class,
                qHsptMenu.menuCode,
                qHsptMenu.menuName,
                qHsptMenu.menuType,
                qHsptMenu.pkFMenu,
                qHsptMenu.pkMenu,
                qHsptMenu.menuUrl,
                qHsptMenu.orderCode,
                qHsptMenu.menuIco,
                qHsptMenu.menuLev,
                qHsptMenu.isEnd
        ))
                .from(qHsptMenu)
                .where(qHsptMenu.dr.eq(BaseConstants.DATA_STATUS_OK))
                .orderBy(qHsptMenu.orderCode.asc())
                .fetch();
        return list;
    }

    public List<UserResourceDTO> getUserResources() throws BaseException {
        //接口资源信息
        QHsptResource res = QHsptResource.hsptResource;
        //权限信息
        QHsptPermissions ip = QHsptPermissions.hsptPermissions;
        //权限资源信息
        QHsptPermissionsResource ips = QHsptPermissionsResource.hsptPermissionsResource;
        //菜单权限信息
        QHsptMenuPermissions imp = QHsptMenuPermissions.hsptMenuPermissions;
        //菜单新
        QHsptMenu im = QHsptMenu.hsptMenu;
        //角色菜单信息
        QHsptRoleMenu irm = QHsptRoleMenu.hsptRoleMenu;
        //角色信息
        QHsptRole ir = QHsptRole.hsptRole;
        //用户角色
        QHsptUserRole iur = QHsptUserRole.hsptUserRole;
        //用户信息
        QHsptUser iu = QHsptUser.hsptUser;

        return getQueryFactory().select(Projections.bean(
                UserResourceDTO.class,
                res.methodName,
                res.methodPath,
                res.modulePath,
                res.moduleId,
                res.url,
                ip.permissionsCode,
                ip.permissionsName,
                im.menuCode,
                im.menuName,
                im.menuType,
                im.menuUrl,
                im.orderCode,
                ir.roleCode,
                ir.roleName,
                ir.roleType
        )).from(res, ips, ip, imp, im, irm, ir, iur, iu)
                .where(res.dr.eq(BaseConstants.DATA_STATUS_OK)
                        .and(ip.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(ips.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(imp.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(im.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(irm.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(ir.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(iur.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(iu.dr.eq(BaseConstants.DATA_STATUS_OK))
                        .and(res.pkResource.eq(ips.pkResource))
                        .and(ips.pkPermissions.eq(ip.pkPermissions))
                        .and(ip.pkPermissions.eq(imp.pkPermissions))
                        .and(im.pkMenu.eq(irm.pkMenu))
                        .and(imp.pkMenu.eq(im.pkMenu))
                        .and(irm.pkRole.eq(ir.pkRole))
                        .and(ir.pkRole.eq(iur.pkRole))
                        .and(iur.pkUser.eq(iu.pkUser)))
                //按照菜单内部排序号返回
                .orderBy(im.orderCode.asc())
                .fetch();
    }

    public List<HsptPermissions> getPermissions(long pkUser) throws BaseException {
        QHsptPermissions qHsptPermissions = QHsptPermissions.hsptPermissions;
        QHsptUserRole qHsptUserRole = QHsptUserRole.hsptUserRole;
        QHsptRolePermission qHsptRolePermission = QHsptRolePermission.hsptRolePermission;
        List<HsptPermissions> permissions = getQueryFactory().select(qHsptPermissions)
            .from(qHsptPermissions, qHsptRolePermission, qHsptUserRole)
            .where(qHsptPermissions.dr.eq(BaseConstants.DATA_STATUS_OK)
                .and(qHsptRolePermission.dr.eq(BaseConstants.DATA_STATUS_OK))
                .and(qHsptUserRole.dr.eq(BaseConstants.DATA_STATUS_OK))
                .and(qHsptPermissions.pkPermissions.eq(qHsptRolePermission.pkPermission))
                .and(qHsptRolePermission.pkRole.eq(qHsptUserRole.pkRole))
                .and(qHsptUserRole.pkUser.eq(pkUser))
            )
            .fetch();
        return permissions;
    }
}
