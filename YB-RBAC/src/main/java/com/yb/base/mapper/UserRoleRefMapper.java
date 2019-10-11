package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.UserRoleRefEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface UserRoleRefMapper extends BaseMapper<UserRoleRefEntity>{
    @Select("select * from user_role_ref where user_id = #{userId}")
    UserRoleRefEntity selectOneByParams(@Param("userId")int userId);
}
