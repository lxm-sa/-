package com.yb.base.vo;

/**
 * Created by Administrator on 2019/8/26.
 */
public class TestTitleVo {



    private int test_title_id;

    private int test_id;

    private int title_id;

    /**
     * 扩展字段
     */

    private String test_title;

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
