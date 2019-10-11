package com.yb.base.vo;

import java.util.Date;

/**
 * Created by Administrator on 2019/8/28.
 */
public class TitleAnswerVo {

    private int title_id;

    private int title_type_id;

    private String title_content;


    private String title_type_name;

    private String titleAnswer;

    public int getTitle_id() {
        return title_id;
    }

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }

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

    public String getTitle_content() {
        return title_content;
    }

    public void setTitle_content(String title_content) {
        this.title_content = title_content;
    }

    public String getTitleAnswer() {
        return titleAnswer;
    }

    public void setTitleAnswer(String titleAnswer) {
        this.titleAnswer = titleAnswer;
    }
}
