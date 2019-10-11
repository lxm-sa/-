package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mayn on 2019/8/28.
 */
@Mapper
public interface AnswerMapper extends BaseMapper<AnswerEntity>{
    @Select("select a.* ,t.title_content ,p.part_name,p.part_spec,p.part_position from  title t inner join answer a on a.title_id = t.title_id" +
            " inner join parts p on p.part_id = a.part_id order by a.answer_id")
    List<AnswerEntity> selectAnswerAll();

}
