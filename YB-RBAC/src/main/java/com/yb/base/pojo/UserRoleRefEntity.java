package com.yb.base.pojo;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_role_ref")
public class UserRoleRefEntity {
  @TableId(value = "user_role_id",type = IdType.AUTO)
  private int user_role_id;
  @TableField(value = "user_id")
  private int user_id;
  @TableField(value = "role_id")
  private int role_id;
  @TableField(value = "update_time")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date update_time;
  @TableField(value = "create_time")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date create_time;
  @TableField(value = "order_number")
  private int order_number;

  public int getUser_role_id() {
    return user_role_id;
  }

  public void setUser_role_id(int user_role_id) {
    this.user_role_id = user_role_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getRole_id() {
    return role_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
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

  public int getOrder_number() {
    return order_number;
  }

  public void setOrder_number(int order_number) {
    this.order_number = order_number;
  }
}
