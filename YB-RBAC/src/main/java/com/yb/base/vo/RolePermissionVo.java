package com.yb.base.vo;

import com.yb.base.pojo.PermissionEntity;

import java.util.List;

/**
 * Created by mayn on 2019/8/21.
 */
public class RolePermissionVo {
    private  int role_id;
    private  String role_name;
    private List<PermissionEntity> perIds;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<PermissionEntity> getPerIds() {
        return perIds;
    }

    public void setPerIds(List<PermissionEntity> perIds) {
        this.perIds = perIds;
    }
}
