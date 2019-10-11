package com.yb.managemodule.service.impl;

import com.yb.base.mapper.RoleMapper;
import com.yb.base.mapper.UserRoleRefMapper;

import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.base.pojo.UserRoleRefEntity;
import com.yb.base.util.Md5Utils;
import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;
import com.yb.base.mapper.UserMapper;
import com.yb.managemodule.service.IUserService;
import org.apache.log4j.Logger;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户管理服务实现类
 * Created by Administrator on 2019/7/24.
 */

@Service
public class UserService implements IUserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class);

    public static final String MESSAGE_PARAM_STATUS_START = "允许用户登录";
    public static final String MESSAGE_PARAM_STATUS_STOP = "禁止用户登录";
    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【UserForm】不能为空";
    public static final String MESSAGE_LOGINIDREPEAT = "该登录账户名已存在";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleRefMapper userRoleRefMapper;
    /**
     * 分页查询，用户列表
     * @return
     */
    @Override
    public List<UserVo> queryPageUserInfo(int order_number) {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> users = userMapper.queryPageUserInfo(order_number);

        for (UserEntity user : users) {
            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo) ;

        }

        return userVos;
    }

    /**
     * 根据条件进行模糊查询，支持分页
     * @param userName
     * @param telphone
     * @return
     */
    @Override
    public List<UserVo> queryPageUserInfoByParams(String userName, String telphone) {

        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> userList = userMapper.queryPageUserInfoByParams(userName, telphone);

        for (UserEntity user : userList) {

            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo);
        }

        return userVos;
    }

    /**
     * 修改用户信息
     * @param userId
     * @param userVo
     * @return
     */
    @Override
    @Transactional
    public Result updateUserById(int userId, UserVo userVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(userId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        UserEntity user = userMapper.selectById(userId);
        user.setUser_name(userVo.getUser_name());
        user.setBirthday(userVo.getBirthday());
        user.setHometowm(userVo.getHometowm());
        user.setIdentity_id(userVo.getIdentity_id());

        user.setSchool(userVo.getSchool());
        user.setSex(userVo.getSex());
        user.setTelphone(userVo.getTelphone());
        user.setUpdate_time(new Date());

        UserRoleRefEntity userRoleRef = userRoleRefMapper.selectOneByParams(userVo.getUser_id());
        RoleEntity role = roleMapper.selectRoleOne(userVo.getRole_name());
        userRoleRef.setRole_id(role.getRole_id());
        userRoleRef.setUpdate_time(new Date());

        try{
            userMapper.updateById(user);
            userRoleRefMapper.updateById(userRoleRef);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }


    /**
     * 修改用户登录状态
     * @param userId
     * @param status
     * @return
     */
    @Override
    public Result updateUserStatusById(int userId, int status) {
        boolean isSuccess = true;
        Result result = new Result();

        UserEntity user = userMapper.selectById(userId);

        user.setStatus(status);
        try {
            userMapper.updateById(user);
            if (1==status) {
                result.setMessage(MESSAGE_PARAM_STATUS_START);
            } else if(2==status){
                result.setMessage(MESSAGE_PARAM_STATUS_STOP);
            }else{
                //
            }

        } catch (Exception ex) {
            isSuccess = false;
        }
        if (!isSuccess) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }
        return result;
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @Override
    @Transactional
    public Result createUserInfo(UserVo userVo) {
        Result result = new Result();
        //验证参数
        if (null == userVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }
        //登录账户唯一验证
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("account_name", userVo.getAccount_name());

            List<UserEntity> userList = userMapper.selectByMap(params);
            if (userList.size() != 0) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_LOGINIDREPEAT);
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_LOGINIDREPEAT);
            return result;
        }
        //构建用户模型
        UserEntity user = new UserEntity();
        UserRoleRefEntity userRoleRef = new UserRoleRefEntity();
        //复制属性赋值给User对象
        BeanUtils.copyProperties(userVo, user);
        Date date = new Date();
        try {
            user.setUpdate_time(date);

            user.setCreate_time(date);

            String userSalt= UUID.randomUUID().toString();

            user.setUser_salt(userSalt);

            String password = Md5Utils.md5(userVo.getPassword(),userSalt);

            user.setPassword(password);

            user.setStatus(2);

            //创建用户表信息
            userMapper.insert(user);
            //获取新增用户的Id信息
            UserEntity selectOne = userMapper.selectOne(user);

            //创建用户角色表信息
            userRoleRef.setUser_id(selectOne.getUser_id());
            userRoleRef.setRole_id(userVo.getRole_id());
            userRoleRef.setCreate_time(date);
            userRoleRef.setUpdate_time(date);

            userRoleRefMapper.insert(userRoleRef);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }


    /**
     * 批量逻辑删除用户
     * @param userIds
     * @return
     */
    @Override
    @Transactional
    public Result deleteUserInfos(Integer[] userIds) {
        Result result = new Result();
        // 验证参数
        if (userIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int userId : userIds) {

                UserEntity user = userMapper.selectById(userId);

                user.setIsdelete(1);

                user.setUpdate_time(new Date());

                userMapper.updateById(user);

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    @Override
    public Result resetPassword(int userId) {
        boolean isSuccess = true;
        Result result = new Result();
        UserEntity user = userMapper.selectById(userId);
        //密码加密
        String password = Md5Utils.md5("123456",user.getUser_salt());

        user.setPassword(password);

        user.setUpdate_time(new Date());

        try {
            userMapper.updateById(user);
            result.setMessage(MESSAGE_PARAM_UPDATE_SUCCESS);

        } catch (Exception ex) {
            isSuccess = false;
        }
        if (!isSuccess) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }

        return result;
    }

    @Override
    public List<UserVo> queryTeacherList() {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> users = userMapper.selectTeacherList();

        for (UserEntity user : users) {
            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo) ;

        }



        return userVos;
    }

    @Override
    public List<UserVo> queryStudentList(int teachId) {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> users = userMapper.selectStudentList(teachId);

        for (UserEntity user : users) {
            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo) ;

        }

        return userVos;
    }

    @Override
    public List<UserVo> queryPageStudentInfoByParams(String userName, String telphone,int teachId) {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> userList = userMapper.queryPageStudentInfoByParams(userName, telphone,teachId);

        for (UserEntity user : userList) {

            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo);
        }

        return userVos;
    }

    @Override
    public List<UserVo> queryClassStudentList() {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> users = userMapper.selectClassStudentList();

        for (UserEntity user : users) {
            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo) ;

        }

        return userVos;
    }

    @Override
    public List<UserVo> queryPageClassStudentInfoByParams(String userName, String telphone) {
        List<UserVo> userVos = new ArrayList<>();

        List<UserEntity> userList = userMapper.queryPageClassStudentInfoByParams(userName, telphone);

        for (UserEntity user : userList) {

            UserVo userVo = new UserVo();

            BeanUtils.copyProperties(user, userVo);

            userVos.add(userVo);
        }

        return userVos;
    }
}
