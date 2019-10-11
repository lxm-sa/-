package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("class")
public class ClassEntity {

  @TableId(value = "class_id",type = IdType.AUTO)
  private int class_id;
  @TableField(value = "class_name")
  private String class_name;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  @TableField(value = "create_time")
  private Date create_time;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  @TableField(value = "update_time")
  private Date update_time;
  @TableField(value = "status")
  private int status;
  @TableField(value = "teach_id")
  private int teach_id;
  /*扩展字段*/
  @TableField(exist = false)
  private String teach_name;

  public int getClass_id() {
    return class_id;
  }

  public void setClass_id(int class_id) {
    this.class_id = class_id;
  }

  public String getClass_name() {
    return class_name;
  }

  public void setClass_name(String class_name) {
    this.class_name = class_name;
  }

  public Date getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Date create_time) {
    this.create_time = create_time;
  }

  public Date getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(Date update_time) {
    this.update_time = update_time;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getTeach_id() {
    return teach_id;
  }

  public void setTeach_id(int teach_id) {
    this.teach_id = teach_id;
  }

  public String getTeach_name() {
    return teach_name;
  }

  public void setTeach_name(String teach_name) {
    this.teach_name = teach_name;
  }
}
