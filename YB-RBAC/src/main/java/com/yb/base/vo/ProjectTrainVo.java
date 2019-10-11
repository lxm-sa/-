package com.yb.base.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Administrator on 2019/8/12.
 */
public class ProjectTrainVo {

    private int pro_id;

    private String pro_name;

    private int sub_id;

    private String remark;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")

    private Date update_time;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")

    private Date create_time;

    private String colunm1;

    private String colunm2;

    private String colunm3;

    /**扩展字段*/
    private String sub_name;

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getColunm1() {
        return colunm1;
    }

    public void setColunm1(String colunm1) {
        this.colunm1 = colunm1;
    }

    public String getColunm2() {
        return colunm2;
    }

    public void setColunm2(String colunm2) {
        this.colunm2 = colunm2;
    }

    public String getColunm3() {
        return colunm3;
    }

    public void setColunm3(String colunm3) {
        this.colunm3 = colunm3;
    }


}
