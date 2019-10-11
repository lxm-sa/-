package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity>{
    @Select("select * from permission")
    List<PermissionEntity> selectPermissionAll();
     @Select("select * from permission where permission_parent=0 ")
    List<PermissionEntity> selectPermission_parent();
    @Select("select * from permission where permission_name like CONCAT('%',#{permission_name},'%')")
    List<PermissionEntity> selectPermission_name(String permission_name);
}
