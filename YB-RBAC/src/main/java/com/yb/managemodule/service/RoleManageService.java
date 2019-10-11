package com.yb.managemodule.service;

import com.yb.base.pojo.RoleEntity;
import com.yb.base.vo.ModularVo;
import com.yb.base.vo.RolePermissionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/21.
 */
public interface RoleManageService {

    List<RolePermissionVo> selectRoleList(int index);

    List<RolePermissionVo> selectRoleName(String role_name);

    List<ModularVo> getRoleAdd();

    int addRole(String role_name, Integer[] permissionId);

    List<RoleEntity> selectGetRoleName(String role_name);

    int deleteRoles(Integer[] roleId);

    Map<String, Object> toRoleEdit(Integer role_id);

   int deleteRoleById(Integer role_id);

    List alterRoleName(String role_name, int role_id);

    int saveEditRole(String role_name, int role_id, Integer[] permissionId);

    double selectRoleCount();
}
