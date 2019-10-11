package com.yb.base.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;


/**
 * Created by mayn on 2019/8/6.
 */
@Data//不用手动创建set,get方法
@TableName("category")
public class CategoryEntity {
    @TableId(value = "category_id",type = IdType.AUTO)
    private int category_id;
    @TableField(value = "category_name")
    private String category_name;
    @TableField(value = "remark")
    private String remark;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
