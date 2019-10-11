package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName( "study")
public class StudyEntity {
    @TableId(value = "study_id",type = IdType.AUTO)
    private int study_id;
    @TableField(value = "user_id")
    private Integer user_id;
    @TableField(value = "barcode_id")
    private String barcode_id;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date create_time;
    @TableField(value = "isdelete")
    private Integer isdelete;

    public int getStudy_id() {
        return study_id;
    }

    public void setStudy_id(int study_id) {
        this.study_id = study_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getBarcode_id() {
        return barcode_id;
    }

    public void setBarcode_id(String barcode_id) {
        this.barcode_id = barcode_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}
