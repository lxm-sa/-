package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/9/9 0009.
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassEntity> {

    @Select("select * from class where teach_id = #{teachId} order by create_time")
    List<ClassEntity> queryClassByTeachId(int teachId);
}
