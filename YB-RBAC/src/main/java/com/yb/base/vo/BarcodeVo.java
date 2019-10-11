package com.yb.base.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.yb.base.pojo.PartsEntity;

/**
 * Created by Administrator on 2019/8/19.
 */
public class BarcodeVo{

    private String barcode_id;

    private String barcode_url;

    private int sub_id;

    private int part_id;

    private int category_id;



    /**扩展字段*/
    private String category_name;

    private String sub_name;

    private int pro_id;

    private String pro_name;

    private int content_id;

    private String content_name;

    private String part_name;

    private String part_position;

    private String part_url;

    private String part_present;

    private String part_principle;

    private Integer part_number;

    private String part_spec;

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

    public String getPart_spec() {
        return part_spec;
    }

    public void setPart_spec(String part_spec) {
        this.part_spec = part_spec;
    }

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

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getContent_name() {
        return content_name;
    }

    public void setContent_name(String content_name) {
        this.content_name = content_name;
    }

    public String getBarcode_id() {
        return barcode_id;
    }

    public void setBarcode_id(String barcode_id) {
        this.barcode_id = barcode_id;
    }

    public String getBarcode_url() {
        return barcode_url;
    }

    public void setBarcode_url(String barcode_url) {
        this.barcode_url = barcode_url;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }


    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
