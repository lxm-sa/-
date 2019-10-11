package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("class_teacher")
public class ClassTeacherEntity {
    @TableId(value = "class_teach_id",type = IdType.AUTO)
    private int class_teach_id;
    @TableField(value = "teacher_id")
    private int teacher_id;
    @TableField(value = "class_id")
    private int class_id;

    /*扩展字段*/
    @TableField(exist = false)
    private String teach_name;
    @TableField(exist = false)
    private String class_name;

    public int getClass_teach_id() {
        return class_teach_id;
    }

    public void setClass_teach_id(int class_teach_id) {
        this.class_teach_id = class_teach_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTeach_name() {
        return teach_name;
    }

    public void setTeach_name(String teach_name) {
        this.teach_name = teach_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
