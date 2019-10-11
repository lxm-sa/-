package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface RoleMapper  extends BaseMapper<RoleEntity>{
    @Select("select * from role")
    List<RoleEntity> selectAllRole();
    @Select("select * from role where role_name like CONCAT('%',#{role_name},'%')")
    List<RoleEntity>selectRoleName(String role_name);
    @Select("select * from role where role_name = #{roleName}")
    RoleEntity selectRoleOne(@Param("roleName")String roleName);
}
