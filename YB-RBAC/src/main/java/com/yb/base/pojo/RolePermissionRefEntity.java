package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName("role_permission_ref")
public class RolePermissionRefEntity {
    @TableId(value = "role_permission_id",type = IdType.AUTO)
    private int role_permission_id;
    @TableField(value = "permission_id")
    private int permission_id;
    @TableField(value = "role_id")
    private int role_id;
    @TableField(value = "order_number")
    private Integer order_number;

    public int getRole_permission_id() {
        return role_permission_id;
    }

    public void setRole_permission_id(int role_permission_id) {
        this.role_permission_id = role_permission_id;
    }

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }
}
