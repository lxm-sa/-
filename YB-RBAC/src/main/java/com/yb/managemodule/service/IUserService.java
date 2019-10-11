package com.yb.managemodule.service;



import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;

import java.util.List;

/**
 * 用户管理服务接口类
 * Created by Administrator on 2019/7/24.
 */
public interface IUserService {

    /**
     * 分页查询，用户列表
     * @return
     */
    List<UserVo> queryPageUserInfo(int order_number);

    /**
     * 根据条件进行模糊查询，支持分页
     * @param userName
     * @param telphone
     * @return
     */
    List<UserVo> queryPageUserInfoByParams(String userName, String telphone);

    /**
     * 修改用户信息
     * @param userId
     * @param userVo
     * @return
     */
    Result updateUserById(int userId, UserVo userVo);

    /**
     * 修改用户登录状态
     *
     * @param userId
     * @param status
     * @return
     */
    Result updateUserStatusById(int userId, int status);

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    Result createUserInfo(UserVo userVo);

    /**
     * 批量逻辑删除用户
     * @param userIds
     * @return
     */
    Result deleteUserInfos(Integer[] userIds);

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    Result resetPassword(int userId);

    /**
     * 查询老师列表
     * @return
     */
    List<UserVo> queryTeacherList();

    /**
     * 查询学生列表
     * @return
     */
    List<UserVo> queryStudentList(int teachId);


    /**
     * 根据条件进行模糊查询学生，支持分页
     * @param userName
     * @param telphone
     * @return
     */
    List<UserVo> queryPageStudentInfoByParams(String userName,String telphone,int teachId);

    List<UserVo> queryClassStudentList();

    List<UserVo> queryPageClassStudentInfoByParams(String userName, String telphone);
}
