package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ClassStudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/9/9 0009.
 */
@Mapper
public interface ClassStudentMapper extends BaseMapper<ClassStudentEntity> {

    @Select("select * from class_student where class_id = #{classId}")
    List<ClassStudentEntity> selectClassStuByParams(@Param("classId")int classId);
}
