package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName( "title")
public class TitleEntity {
    @TableId(value = "title_id",type = IdType.AUTO)
    private int title_id;
    @TableField(value = "title_type_id")
    private int title_type_id;
    @TableField(value = "title_content")
    private String title_content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @TableField(value = "update_time")
    private Date update_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @TableField(value = "create_time")
    private Date create_time;
    @TableField(value = "isdelete")
    private int isdelete;

    /*扩展字段*/
    @TableField(exist = false)
    private String title_type_name;
    /*扩展字段*/
    @TableField(exist = false)
    private int part_id;
    @TableField(exist = false)
    private String part_name;

    public int getTitle_id() {
        return title_id;
    }

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }

    public int getTitle_type_id() {
        return title_type_id;
    }

    public void setTitle_type_id(int title_type_id) {
        this.title_type_id = title_type_id;
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

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public String getTitle_type_name() {
        return title_type_name;
    }

    public void setTitle_type_name(String title_type_name) {
        this.title_type_name = title_type_name;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }
}
