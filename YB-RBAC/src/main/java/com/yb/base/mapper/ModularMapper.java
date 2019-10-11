package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ModularEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by mayn on 2019/8/6.
 */
@Mapper
public interface ModularMapper  extends BaseMapper<ModularEntity>{
    @Select("select * from  modular")
    List<ModularEntity> selectModularAll();
    @Select("select * from modular where modular_name like CONCAT('%',#{modular_name},'%')")
    List<ModularEntity> selectModular_name(String modular_name);
}
