package com.yb.managemodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.ModularMapper;
import com.yb.base.mapper.PermissionMapper;
import com.yb.base.pojo.ModularEntity;
import com.yb.managemodule.service.ModularService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by mayn on 2019/8/26.
 */
@Service
public class ModularServiceImpl  implements ModularService{
    @Autowired
    private ModularMapper modularMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<ModularEntity> toModularList(int index) {
        RowBounds rowBounds = new RowBounds((index-1)*8,8);
        Wrapper wrapper=new EntityWrapper();
        wrapper.orderBy("modular_id");
        List list = modularMapper.selectMapsPage(rowBounds, wrapper);
        return list;
    }

    @Override
    public double selectModularCount() {
        Wrapper wrapper= new EntityWrapper();
        Integer count = modularMapper.selectCount(wrapper);
        double countpage= Math.ceil(count/8.0);
        return countpage;
    }

    @Override
    public int addModular(ModularEntity modularEntity) {
        modularEntity.setCreate_time(new Date());
        modularEntity.setUpdata_time(new Date());
        Integer insert = modularMapper.insert(modularEntity);
        return insert;
    }

    @Override
    public List<ModularEntity> selectModular_name(String modular_name) {
        List<ModularEntity> modularEntities = modularMapper.selectModular_name(modular_name);
        return modularEntities;
    }

    @Override
    public int deleteCheckModulars(Integer[] modularId) {
        Integer integer=0;
        for(int i=0;i<modularId.length;i++){
            integer = modularMapper.deleteById(modularId[i]);
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("modular_id",modularId[i]);
            permissionMapper.delete(wrapper);
            if (integer==0){
                return 0;
            }
        }
        return integer;
    }

    @Override
    public int toModularDeleteById(Integer modular_id) {
        Integer integer = modularMapper.deleteById(modular_id);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("modular_id",modular_id);
         permissionMapper.delete(wrapper);
        return integer;
    }

    @Override
    public ModularEntity toModularEdit(Integer modular_id) {
        ModularEntity modularEntity = modularMapper.selectById(modular_id);
        return modularEntity;
    }

    @Override
    public Integer saveModular(ModularEntity modularEntity) {
        modularEntity.setUpdata_time(new Date());
        Integer integer = modularMapper.updateById(modularEntity);
        return integer;
    }
}
