package com.yb.managemodule.service;

import com.yb.base.pojo.ModularEntity;

import java.util.List;

/**
 * Created by mayn on 2019/8/26.
 */
public interface ModularService {
    List<ModularEntity> toModularList(int index);

    double selectModularCount();

    int addModular(ModularEntity modularEntity);

    List<ModularEntity> selectModular_name(String modular_name);

    int deleteCheckModulars(Integer[] modularId);

    int toModularDeleteById(Integer modular_id);

    ModularEntity toModularEdit(Integer modular_id);

    Integer saveModular(ModularEntity modularEntity);
}
