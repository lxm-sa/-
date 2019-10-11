package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.PartsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/13.
 */
@Mapper
public interface PartsMapper extends BaseMapper<PartsEntity> {

    @Select("select * from parts where part_name like CONCAT('%',#{partName},'%')")
    List<PartsEntity> queryPagePartsInfoByParams(@Param("partName")String partName);

    @Select("select * from parts where part_name = #{partName}")
    PartsEntity selectPartByParam(@Param("partName")String partName);
}
