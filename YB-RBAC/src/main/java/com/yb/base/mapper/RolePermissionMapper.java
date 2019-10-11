package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.RolePermissionRefEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionRefEntity>{

}
