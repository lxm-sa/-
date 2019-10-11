package com.yb.loginmodule.service;

import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserRoleRefEntity;

/**
 * Created by mayn on 2019/8/13.
 */
public interface RoleService {
    int addRole(UserRoleRefEntity userRoleRefEntity);
}
