package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mayn on 2019/8/6.
 */

@Data//不用手动创建set,get方法
@TableName("stu_answer")
public class StuAnswerEntity {
    @TableId(value = "stu_answer_id",type = IdType.AUTO)
    private int stu_answer_id;
    @TableField(value = "user_id")
    private int user_id;
    @TableField(value = "test_id")
    private int test_id;
    @TableField(value = "title_id")
    private int title_id;
    @TableField(value = "stu_answer_barcode")
    private String stu_answer_barcode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time")
    private Date update_time;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time")
    private Date create_time;
    @TableField(value = "status")
    private int status;
    @TableField(value = "count")
    private int count;

    /**
     * 扩展字段
     */
    @TableField(exist = false)
    private String user_name;
    @TableField(exist = false)
    private String test_title;
    @TableField(exist = false)
    private String title_content;
    @TableField(exist = false)
    private int part_id;
    @TableField(exist = false)
    private String part_name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStu_answer_id() {
        return stu_answer_id;
    }

    public void setStu_answer_id(int stu_answer_id) {
        this.stu_answer_id = stu_answer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getTitle_id() {
        return title_id;
    }

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }

    public String getStu_answer_barcode() {
        return stu_answer_barcode;
    }

    public void setStu_answer_barcode(String stu_answer_barcode) {
        this.stu_answer_barcode = stu_answer_barcode;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTest_title() {
        return test_title;
    }

    public void setTest_title(String test_title) {
        this.test_title = test_title;
    }

    public String getTitle_content() {
        return title_content;
    }

    public void setTitle_content(String title_content) {
        this.title_content = title_content;
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }
}
