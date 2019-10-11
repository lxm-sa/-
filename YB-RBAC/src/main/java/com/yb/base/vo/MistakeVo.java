package com.yb.base.vo;

/**
 * Created by mayn on 2019/8/28.
 */
public class MistakeVo {
    private int title_id;
    private String title_content;
    private String title_type_name;
    private String  part_name;

    public int getTitle_id() {
        return title_id;
    }

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }

    public String getTitle_content() {
        return title_content;
    }

    public void setTitle_content(String title_content) {
        this.title_content = title_content;
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
