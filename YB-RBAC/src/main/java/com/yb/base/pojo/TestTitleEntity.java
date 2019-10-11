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
@TableName("test_title")
public class TestTitleEntity {
    @TableId(value = "test_title_id",type = IdType.AUTO)
    private int test_title_id;
    @TableField(value = "test_id")
    private int test_id;
    @TableField(value = "title_id")
    private int title_id;

    /**
     * 扩展字段
     */
    @TableField(exist = false)
    private String test_title;
    @TableField(exist = false)
    private String title_content;

    public int getTest_title_id() {
        return test_title_id;
    }

    public void setTest_title_id(int test_title_id) {
        this.test_title_id = test_title_id;
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
}
