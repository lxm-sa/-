package com.yb.managemodule.service;

import com.yb.base.pojo.ModularEntity;
import com.yb.base.pojo.PermissionEntity;
import com.yb.base.vo.PermissionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/24.
 */
public interface PermissionService {
    List<PermissionVo> selectPerList(int index);

    double selectPerCount();

    List<ModularEntity> selectModular();

    List<PermissionEntity> selectPermission_parent();

    int addPermission(PermissionEntity permissionEntity);

    int deleteCheckPermissions(Integer[] permissionId);

    List<PermissionVo> selectPermission_name(String permission_name);

    Map<String,Object> toPermissionEdit(Integer permission_id);

    int deletePermissionById(Integer permission_id);

    int savePermission(PermissionEntity permissionEntity);
}
