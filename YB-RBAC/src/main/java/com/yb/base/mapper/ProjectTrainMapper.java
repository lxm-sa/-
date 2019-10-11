package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ProjectTrainEntity;
import com.yb.base.vo.ProjectTrainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */
@Mapper
public interface ProjectTrainMapper extends BaseMapper<ProjectTrainEntity> {

    /**
     * 分页查询，实训项目列表
     * @return
     */
    @Select("select p.*,s.sub_name from project_train p , `subject` s where p.sub_id = s.sub_id and s.isdelete = 0 ")
    List<ProjectTrainEntity> queryPageProjectTrainInfo();

    @Select("select * from project_train where pro_name = #{proName}")
    ProjectTrainEntity selectProOneByParam(@Param("proName") String proName);
}
