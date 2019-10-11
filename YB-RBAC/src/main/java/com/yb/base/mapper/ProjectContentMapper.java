package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ProjectContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/14.
 */
@Mapper
public interface ProjectContentMapper extends BaseMapper<ProjectContentEntity> {


    @Select("select pc.* ,p.pro_name from project_content pc , project_train p where pc.pro_id = p.pro_id order by pc.create_time DESC")
   List<ProjectContentEntity> selectProContetAll();


}
