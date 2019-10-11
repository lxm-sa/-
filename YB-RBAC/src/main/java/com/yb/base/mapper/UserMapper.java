package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity>{
    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and r.order_number>=#{order_number} and " +
            "ur.role_id = r.role_id and u.isdelete = 0 order by u.create_time DESC")
    List<UserEntity> queryPageUserInfo(int order_number);


    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.user_name like CONCAT('%',#{userName},'%') and u.telphone like CONCAT('%',#{telphone},'%') " +
            "and u.isdelete = 0")
    List<UserEntity> queryPageUserInfoByParams(@Param("userName")String userName, @Param("telphone")String telphone);

    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.user_name like CONCAT('%',#{userName},'%') and u.telphone like CONCAT('%',#{telphone},'%') " +
            "and u.isdelete = 0 and r.role_id ='10001' and u.user_id in " +
            "(select cs.stu_id from class_student cs,class c where cs.class_id = c.class_id and c.teach_id = #{teachId} and c.status=0) " +
            " order by u.create_time DESC")
    List<UserEntity> queryPageStudentInfoByParams(@Param("userName") String userName, @Param("telphone") String telphone,@Param("teachId")int teachId);


    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.isdelete = 0 and r.role_id =10003 order by u.create_time DESC")
    List<UserEntity> selectTeacherList();

    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.isdelete = 0 and r.role_id ='10001' and u.user_id in " +
            "(select cs.stu_id from class_student cs,class c where cs.class_id = c.class_id and c.teach_id = #{teachId} and c.status=0) " +
            "order by u.create_time DESC")
    List<UserEntity> selectStudentList(@Param("teachId")int teachId);
    @Options(useGeneratedKeys = true,keyProperty = "user_id",keyColumn = "user_id")
    @Insert("insert into user(user_name,account_name,password,user_salt,identity_id,birthday,sex,hometowm,school,telphone,update_time,create_time)" +
            "values(#{user_name},#{account_name},#{password},#{user_salt},#{identity_id},#{birthday},#{sex},#{hometowm},#{school},#{telphone},#{update_time},#{create_time})")
    Integer insertUser(UserEntity user);

    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.isdelete = 0 and r.role_id ='10001' " +
            "order by u.create_time DESC")
    List<UserEntity> selectClassStudentList();


    @Select("select u.*,r.role_name from user u,role r,user_role_ref ur where u.user_id = ur.user_id and " +
            "ur.role_id = r.role_id and u.user_name like CONCAT('%',#{userName},'%') and u.telphone like CONCAT('%',#{telphone},'%') " +
            "and u.isdelete = 0 and r.role_id ='10001' order by u.create_time DESC")
    List<UserEntity> queryPageClassStudentInfoByParams(@Param("userName") String userName, @Param("telphone") String telphone);
}
