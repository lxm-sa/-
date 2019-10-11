package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;


/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName("permission")
public class PermissionEntity {
    @TableId(value = "permission_id", type = IdType.AUTO)
    private int permission_id;
    @TableField(value = "permission_name")
    private String permission_name;
    @TableField(value = "permission_action")
    private String permission_action;
    @TableField(value = "permission_parent")
    private Long permission_parent;
    @TableField(value = "permission_key")
    private String permission_key;
    @TableField(value = "modular_id")
    private Long modular_id;
    @TableField(value = "update_time")
    private Date update_time;
    @TableField(value = "create_time")
    private Date create_time;

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
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

    public Long getModular_id() {
        return modular_id;
    }

    public void setModular_id(Long modular_id) {
        this.modular_id = modular_id;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}