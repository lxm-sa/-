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
@TableName("learning _resource")
public class LearningResourceEntity {
    @TableId(value = "learning _resource_id",type = IdType.AUTO)
    private int learningResource;
    @TableField(value = "category_id")
    private Integer categoryId;
    @TableField(value = "barcode_id")
    private Integer barcodeId;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "isdelete")
    private Integer isdelete;


}
