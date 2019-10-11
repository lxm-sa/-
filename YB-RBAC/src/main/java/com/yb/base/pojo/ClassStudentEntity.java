package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("class_student")
public class ClassStudentEntity {
  @TableId(value = "class_stu_id",type = IdType.AUTO)
  private int class_stu_id;
  @TableField(value = "class_id")
  private int class_id;
  @TableField(value = "stu_id")
  private int stu_id;

  /**
   * 扩展字段
   */
  @TableField(exist = false)
  private String stu_name;
  @TableField(exist = false)
  private String class_name;

  public int getClass_stu_id() {
    return class_stu_id;
  }

  public void setClass_stu_id(int class_stu_id) {
    this.class_stu_id = class_stu_id;
  }

  public int getClass_id() {
    return class_id;
  }

  public void setClass_id(int class_id) {
    this.class_id = class_id;
  }

  public int getStu_id() {
    return stu_id;
  }

  public void setStu_id(int stu_id) {
    this.stu_id = stu_id;
  }

  public String getStu_name() {
    return stu_name;
  }

  public void setStu_name(String stu_name) {
    this.stu_name = stu_name;
  }

  public String getClass_name() {
    return class_name;
  }

  public void setClass_name(String class_name) {
    this.class_name = class_name;
  }
}
