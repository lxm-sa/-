package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ContentPartsEntity;
import com.yb.base.vo.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/8/14.
 */
@Mapper
public interface ContentPartsMapper extends BaseMapper<ContentPartsEntity> {

    @Select("select part_id from content_parts where content_id = #{contentId}")
    List<ContentPartsEntity> selectContentPartsByParams(@Param("contentId") int contentId);


    @Delete("delete from content_parts where content_id = #{contentId} and part_id =#{partId}")
    Result deleteContentPartsInfo(@Param("contentId") int contentId, @Param("partId") int partId);

    @Select("select p.pro_name,p.sub_id,s.content_name, s.pro_id FROM project_train p,(SELECT * from project_content where content_id in(SELECT content_id from content_parts where part_id=#{part_id}))s where p.pro_id=s.pro_id and p.sub_id=301")
    List<Map<String,Object>> selectByPart_id(Integer part_id);


    @Select("select * from content_parts where content_id = #{contentId} and part_id =#{partId}")
    ContentPartsEntity selectContentPartsInfo(@Param("contentId") int contentId, @Param("partId") int partId);
}
