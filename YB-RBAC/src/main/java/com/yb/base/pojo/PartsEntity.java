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
@TableName( "parts")
public class PartsEntity {
    @TableId(value = "part_id")
    private int part_id;
    @TableField(value = "part_name")
    private String part_name;
    @TableField(value = "part_position")
    private String part_position;
    @TableField(value = "part_url")
    private String part_url;
    @TableField(value = "part_present")
    private String part_present;
    @TableField(value = "part_principle")
    private String part_principle;
    @TableField(value = "part_number")
    private Integer part_number;
    @TableField(value = "order_number")
    private Integer order_number;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time")
    private Date update_time;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time")
    private Date create_time;
    @TableField(value = "part_spec")
    private String part_spec;
    @TableField(value = "position_url")
    private String position_url;

    public String getPosition_url() {
        return position_url;
    }

    public void setPosition_url(String position_url) {
        this.position_url = position_url;
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

    public String getPart_position() {
        return part_position;
    }

    public void setPart_position(String part_position) {
        this.part_position = part_position;
    }

    public String getPart_url() {
        return part_url;
    }

    public void setPart_url(String part_url) {
        this.part_url = part_url;
    }

    public String getPart_present() {
        return part_present;
    }

    public void setPart_present(String part_present) {
        this.part_present = part_present;
    }

    public String getPart_principle() {
        return part_principle;
    }

    public void setPart_principle(String part_principle) {
        this.part_principle = part_principle;
    }

    public Integer getPart_number() {
        return part_number;
    }

    public void setPart_number(Integer part_number) {
        this.part_number = part_number;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
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

    public String getPart_spec() {
        return part_spec;
    }

    public void setPart_spec(String part_spec) {
        this.part_spec = part_spec;
    }
}
