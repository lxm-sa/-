package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.TitleTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by mayn on 2019/8/28.
 */
@Mapper
public interface TitleTypeMapper extends BaseMapper<TitleTypeEntity>{
    @Select("select * from title_type where title_type_name = #{typeName}")
    TitleTypeEntity selectTitleTypeByName(@Param("typeName")String typeName);
}
