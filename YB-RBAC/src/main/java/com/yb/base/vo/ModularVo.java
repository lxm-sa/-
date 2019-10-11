package com.yb.base.vo;

import com.yb.base.pojo.PermissionEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/22.
 */
public class ModularVo {
    private int modular_id;
    private String modular_name;
    private List<PermissionEntity> pers;

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

    public List<PermissionEntity> getPers() {
        return pers;
    }

    public void setPers(List<PermissionEntity> pers) {
        this.pers = pers;
    }
}
