package com.yb.loginmodule.service;

import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/7/25.
 */
public interface UserService {
    Map<String,Object> authcUser(Object account);

    int addUser(UserEntity userEntity);

   List<UserEntity> selectAccountName(String accountName);

    List<RoleEntity> selectAllRole();

    List selectalterAccountName(String accountName, int id);

    int saveuser(UserEntity user);
}
