package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.TestTitleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/26.
 */

@Mapper
public interface TestTitleMapper extends BaseMapper<TestTitleEntity> {


    @Select("select * from test_title where test_id = #{testId} order by rand() limit #{titleCount}")
    List<TestTitleEntity> selectTestTitleByRand(@Param("testId") int testId, @Param("titleCount") int titleCount);

    @Select("select * from test_title where test_id = #{testId} limit #{titleCount}")
    List<TestTitleEntity> selectTestTitleByParams(@Param("testId") int testId, @Param("titleCount") int titleCount);


    @Select("select tt.*,t.title_content from test_title tt,title t where tt.title_id=t.title_id and test_id = #{testId} and isdelete = 0 order by t.title_id")
    List<TestTitleEntity> selectTestTitleByTestId(@Param("testId") int testId);
}
