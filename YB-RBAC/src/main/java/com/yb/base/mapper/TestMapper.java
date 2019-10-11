package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/8/26.
 */
@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {


    @Select("select t.*, s.sub_name,u.user_name from test t,subject s,user u " +
            "where t.sub_id = s.sub_id and t.teacher_id = u.user_id and t.teacher_id = #{teachId} and t.isdelete = 0 ")
    List<TestEntity> selectTestList(@Param("teachId")int teachId);

   @Select("select t.title_id,t.title_content,p.part_name,b.barcode_id from title t,answer a,parts p,barcode b where t.title_id in(SELECT title_id from test_title where test_id=#{test_id}) and a.title_id=t.title_id and p.part_id=a.part_id and b.part_id=p.part_id")
   List<Map <String,Object>> selectTestById(int test_id);
}
