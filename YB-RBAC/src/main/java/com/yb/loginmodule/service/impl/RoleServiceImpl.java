package com.yb.loginmodule.service.impl;

import com.yb.base.mapper.RoleMapper;
import com.yb.base.mapper.UserRoleRefMapper;
import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserRoleRefEntity;
import com.yb.loginmodule.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mayn on 2019/8/13.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleRefMapper userRoleRefMapper;
    @Override
    public int addRole(UserRoleRefEntity userRoleRefEntity) {
        Integer number = userRoleRefMapper.insert(userRoleRefEntity);
        return number;
    }
}
