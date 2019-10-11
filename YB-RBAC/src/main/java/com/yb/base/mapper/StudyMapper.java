package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.StudyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/29.
 */
@Mapper
public interface StudyMapper extends BaseMapper<StudyEntity>{
    @Select("SELECT *,count(create_time)count from study where user_id=#{user_id} GROUP BY DATE_FORMAT(create_time,'%Y-%D-%D') ORDER BY create_time")
    List<Map<String,Object>> selectlearningByUserId(int user_id);
    @Select("SELECT  b.barcode_id,p.*,s.sub_name,c.category_name from barcode b,parts p,subject s,category c where b.barcode_id NOT in(select barcode_id from study where user_id=#{user_id}) and b.part_id=p.part_id and b.sub_id=s.sub_id AND c.category_id=b.category_id  LIMIT #{offset},#{limit}")
    List<Map<String,Object>> selectpageList(@Param("offset") int offset, @Param("limit") int limit, @Param("user_id") int user_id);
}
