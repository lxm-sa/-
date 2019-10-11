package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.StuTestEntity;
import com.yb.base.vo.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/26.
 */

@Mapper
public interface StuTestMapper extends BaseMapper<StuTestEntity> {
    /**
     * 查询参考学员
     * @param testId
     * @return
     */
    @Select("select * from stu_test where test_id = #{testId}")
    List<StuTestEntity> selectStuTestByParams(@Param("testId") int testId);

    @Delete("delete from stu_test where test_id = #{testId} and stu_id =#{stuId}")
    Result deleteStuTestInfos(@Param("testId") int testId, @Param("stuId") int stuId);

    /**
     * 查询待考试卷
     * @param stuId
     * @return
     */
    @Select("select t.*,ut.user_name as teach_name,us.user_name as stu_name,sub.sub_name,st.* from \n" +
            "test t inner join user ut on t.teacher_id = ut.user_id \n" +
            "inner join stu_test st on st.test_id = t.test_id \n" +
            "inner join user us on st.stu_id = us.user_id \n" +
            "inner join subject sub on sub.sub_id = t.sub_id where st.test_count > 0 and st.status <>2 and t.isdelete=0 and us.user_id = #{stuId} and SYSDATE()< t.end_time")
    List<StuTestEntity> selectStuTestByStuId(@Param("stuId") int stuId);


    @Select("select * from stu_test where test_id = #{testId} and stu_id =#{stuId}")
    StuTestEntity selectStuTestInfoByParams(@Param("testId") int testId, @Param("stuId") int stuId);


    @Select("select t.*,ut.user_name as teach_name,us.user_name as stu_name,sub.sub_name,st.* from \n" +
            "test t inner join user ut on t.teacher_id = ut.user_id \n" +
            "inner join stu_test st on st.test_id = t.test_id \n" +
            "inner join user us on st.stu_id = us.user_id \n" +
            "inner join subject sub on sub.sub_id = t.sub_id where st.status =2 and us.user_id = #{stuId}")
    List<StuTestEntity> selectExpireTestByStuId(@Param("stuId") int stuId);

}
