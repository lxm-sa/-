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
@TableName("title_type")
public class TitleTypeEntity {
    @TableId(value = "title_type_id",type = IdType.AUTO)
    private int title_type_id;
    @TableField(value = "title_type_name")
    private String title_type_name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time")
    private Date update_time;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time")
    private Date create_time;

    public int getTitle_type_id() {
        return title_type_id;
    }

    public void setTitle_type_id(int title_type_id) {
        this.title_type_id = title_type_id;
    }

    public String getTitle_type_name() {
        return title_type_name;
    }

    public void setTitle_type_name(String title_type_name) {
        this.title_type_name = title_type_name;
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
