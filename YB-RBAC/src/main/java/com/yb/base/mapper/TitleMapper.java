package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.TitleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * Created by mayn on 2019/8/28.
 */
@Mapper
public interface TitleMapper  extends BaseMapper<TitleEntity>{

    @Select("select t.* ,ty.title_type_name from title t,title_type ty where t.title_type_id=ty.title_type_id and t.isdelete = 0 order by t.title_id")
    List<TitleEntity> selectTitleAll();

    @Select("select t.* ,ty.title_type_name,p.part_id,p.part_name from title t,title_type ty,answer a, parts p where t.title_type_id=ty.title_type_id and " +
            "t.title_id = a.title_id and a.part_id = p.part_id and t.title_id = #{titleId} and  t.isdelete = 0 order by t.title_id")
    List<TitleEntity> selectTitleById(@Param("titleId")int titleId);

    @Select("select t.* ,ty.title_type_name from title t,title_type ty,answer a where t.title_type_id=ty.title_type_id and " +
            "t.title_id = a.title_id and t.isdelete = 0 order by t.title_id")
    Set<TitleEntity> selectTitleList();


    @Select("select t.*,ty.title_type_name  from title t,title_type ty where t.title_type_id=ty.title_type_id and " +
            "t.title_content like CONCAT('%',#{titleContent},'%') and ty.title_type_name like CONCAT('%',#{titleTypeName},'%') and t.isdelete = 0 order by t.title_id")
    List<TitleEntity> selectTitleListByParams(@Param("titleContent")String titleContent, @Param("titleTypeName")String titleTypeName);
}
