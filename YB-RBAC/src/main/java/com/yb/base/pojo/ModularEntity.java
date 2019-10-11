package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName( "modular")
public class ModularEntity {
    @TableId(value = "modular_id",type = IdType.AUTO)
    private int modular_id;
    @TableField(value = "modular_name")
    private String modular_name;
    @TableField(value = "order_number")
    private Long order_number;
    @TableField(value = "modular_default_status")
    private String modular_default_status;
    @TableField(value = "updata_time")
    private Date updata_time;
    @TableField(value = "create_time")
    private Date create_time;

    public int getModular_id() {
        return modular_id;
    }

    public void setModular_id(int modular_id) {
        this.modular_id = modular_id;
    }

    public String getModular_name() {
        return modular_name;
    }

    public void setModular_name(String modular_name) {
        this.modular_name = modular_name;
    }

    public Long getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Long order_number) {
        this.order_number = order_number;
    }

    public String getModular_default_status() {
        return modular_default_status;
    }

    public void setModular_default_status(String modular_default_status) {
        this.modular_default_status = modular_default_status;
    }

    public Date getUpdata_time() {
        return updata_time;
    }

    public void setUpdata_time(Date updata_time) {
        this.updata_time = updata_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
