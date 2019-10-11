package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */
@Mapper
public interface SubjectMapper extends BaseMapper<SubjectEntity> {

    @Select("select * from `subject` where isdelete = 0")
    List<SubjectEntity> selectSubjectAll();

    @Select("select * from `subject` where sub_name = #{subName} and isdelete=0")
    SubjectEntity selectSubjectOne(@Param("subName") String subName);
}
