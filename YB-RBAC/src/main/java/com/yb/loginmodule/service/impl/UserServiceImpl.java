package com.yb.loginmodule.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.*;
import com.yb.base.pojo.*;
import com.yb.base.util.Md5Utils;
import com.yb.loginmodule.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mayn on 2019/7/25.
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
  @Autowired
   private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRefMapper userRoleRefMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private ModularMapper modularMapper;
    @Override
    public Map<String, Object> authcUser(Object account) {
        Map<String,Object> userMap = new HashMap<>();
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("account_name",account);
        List<UserEntity> users = userMapper.selectList(wrapper);
        if (users.size()!=0){
            UserEntity user = users.get(0);
            userMap.put("user",user);
            int userId = user.getUser_id();
            Wrapper wrapper1 = new EntityWrapper();
            wrapper1.eq("user_id",userId);
            List<UserRoleRefEntity> userRoleRefs = userRoleRefMapper.selectList(wrapper1);

            if (userRoleRefs!=null) {
                for (UserRoleRefEntity userRoleRef : userRoleRefs) {
                    int roleId = userRoleRef.getRole_id();
                    RoleEntity role = roleMapper.selectById(roleId);
                    if (role==null){
                        continue;
                    }
                    userMap.put("role",role);
                    Wrapper wrapper2 = new EntityWrapper();
                    wrapper2.eq("role_id", roleId);
                    List<RolePermissionRefEntity> rolePermissionRefEntities = rolePermissionMapper.selectList(wrapper2);
                        Map<String, Integer> permissions = new HashMap<>();
                        List permissionIds = new ArrayList();
                        for (RolePermissionRefEntity rolePermissionRefEntity : rolePermissionRefEntities) {
                            int permissionId = rolePermissionRefEntity.getPermission_id();

                        if (permissions.get("1")==null) {
                            permissions.put(rolePermissionRefEntity.getRole_permission_id() + "", rolePermissionRefEntity.getPermission_id());
                            permissionIds.add(permissionId);
                        } else {
                            break;
                        }
                    }

                    List<PermissionEntity> permissionList = permissionMapper.selectBatchIds(permissionIds);
                    userMap.put("permission",permissionList);
                    List modularIds = new ArrayList();
                    Map<String, Object>  modularIdMap= new HashMap<>();
                    for (PermissionEntity permission: permissionList) {
                        Long modularId = permission.getModular_id();
                        if (modularIdMap.get(modularId+"")==null){
                            modularIdMap.put(modularId+"",modularId);
                            modularIds.add(modularId);
                        }else{
                            continue;
                        }
                    }
                    List modulars = modularMapper.selectBatchIds(modularIds);
                    userMap.put("modulars",modulars);
                }
            }
        }
        return userMap;
    }

    @Override
    public int addUser(UserEntity user) {
        user.setUpdate_time(new Date());
        user.setCreate_time(new Date());
        String userSalt= UUID.randomUUID().toString();
        user.setUser_salt(userSalt);
        String password = user.getPassword();
        String md5Password = Md5Utils.md5(password, userSalt);
        user.setPassword(md5Password);
        Integer number = userMapper.insertUser(user);
        return number;
    }

    @Override
    public List<UserEntity> selectAccountName(String accountName) {
        EntityWrapper<UserEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("account_name",accountName);
        List<UserEntity> userEntities = userMapper.selectList(wrapper);
        return userEntities;
    }

    @Override
    public List<RoleEntity> selectAllRole() {
        List<RoleEntity> list = roleMapper.selectAllRole();
        return list;
    }

    @Override
    public List selectalterAccountName(String account_name, int id) {
        Wrapper<UserEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("account_name",account_name);
        wrapper.notIn("user_id",id);
        List<UserEntity> users = userMapper.selectList(wrapper);
        return users;
    }

    @Override
    public int saveuser(UserEntity user) {
        user.setUpdate_time(new Date());
        Integer number = userMapper.updateById(user);
        return number;
    }


}
