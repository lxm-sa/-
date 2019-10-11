package com.yb.base.vo;

import com.yb.base.pojo.PermissionEntity;

/**
 * Created by mayn on 2019/8/24.
 */
public class PermissionVo {

    private Long modular_id;
    private String modular_name;
    private int permission_id;
    private String permission_name;
    private String permission_action;
    private Long permission_parent;
    private String permission_key;

    public Long getModular_id() {
        return modular_id;
    }

    public void setModular_id(Long modular_id) {
        this.modular_id = modular_id;
    }

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    public String getModular_name() {
        return modular_name;
    }

    public void setModular_name(String modular_name) {
        this.modular_name = modular_name;
    }


    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getPermission_action() {
        return permission_action;
    }

    public void setPermission_action(String permission_action) {
        this.permission_action = permission_action;
    }

    public Long getPermission_parent() {
        return permission_parent;
    }

    public void setPermission_parent(Long permission_parent) {
        this.permission_parent = permission_parent;
    }

    public String getPermission_key() {
        return permission_key;
    }

    public void setPermission_key(String permission_key) {
        this.permission_key = permission_key;
    }
}
