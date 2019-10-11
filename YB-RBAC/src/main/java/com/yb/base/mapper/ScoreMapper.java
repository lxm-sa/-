package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.ScoreEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/9.
 */
@Mapper
public interface ScoreMapper extends BaseMapper<ScoreEntity>{
    @Select("SELECT * from(select s.score_id,s.score,s.create_time,u.user_name,t.test_title,t.test_id,s.test_time,s.user_id,s.score_number from class_student cs,score s,test t,user u,stu_test st WHERE cs.class_id=#{class_id} and s.isdelete=0 and st.test_id=s.test_id and st.stu_id=s.user_id and s.test_id=t.test_id and s.user_id=cs.stu_id and u.user_id=s.user_id and t.test_title=#{test_title} ORDER BY score DESC)t GROUP BY t.user_id ORDER BY t.score DESC ")
    List<Map<String,Object>> selectTestScoresList(@Param("class_id") int class_id, @Param("test_title") String test_title);
    @Select("SELECT COUNT(*) from(SELECT * from(select s.score_id,s.score,s.create_time,u.user_name,t.test_title,s.test_time,s.user_id from class_student cs,score s,test t,user u WHERE cs.class_id=#{class_id} and s.test_id=t.test_id and s.user_id=cs.stu_id and u.user_id=s.user_id and t.test_title=#{test_title} ORDER BY score DESC)t GROUP BY t.user_id ORDER BY t.score desc)tt")
    Integer selectTestScoresCount(@Param("class_id") int class_id, @Param("test_title") String test_title);
    @Select("select cs.class_id,s.score_id,u.user_name,t.test_title,s.score from score s,user u,test t,class_student cs WHERE s.score_id=#{score_id} and cs.class_id=#{class_id} and s.user_id=u.user_id and s.test_id=t.test_id and cs.stu_id=s.user_id")
    Map<String,Object> selectTestScoresEdit(@Param("score_id")int score_id,@Param("class_id") int class_id);
    @Select("select COUNT(s.score_id) count,c.class_id, c.class_name,t1.test_title,AVG(s.score) avg,MAX(s.score)max,MIN(s.score) min from class_student cs,class c,(select * from(select SS.* from score ss,stu_test ut where ss.isdelete='0' and ut.stu_id=ss.user_id and ut.test_id=ss.test_id ORDER BY score desc)t GROUP BY test_id,user_id) s ,test t1 where cs.class_id in( select class_id from class where teach_id =#{teach_id}) and c.class_id=cs.class_id and s.user_id=cs.stu_id and t1.test_id=s.test_id GROUP BY c.class_id,t1.test_title ORDER BY c.create_time,t1.create_time desc LIMIT #{offset},#{limit}")
    List<Map<String,Object>> selectClassList(@Param("teach_id") int teach_id, @Param("offset") int offset, @Param("limit") int limit);
    @Select("select COUNT(*) from (select COUNT(*) from class_student cs,class c,(select * from(select * from score ORDER BY score desc)t GROUP BY test_id,user_id) s ,test t1 where cs.class_id in( select class_id from class where teach_id =#{user_id}) and c.class_id=cs.class_id and s.user_id=cs.stu_id and t1.test_id=s.test_id GROUP BY c.class_id,t1.test_title ORDER BY c.create_time)tt")
    Integer selectClassCount(int user_id);
    @Select("select COUNT(s.score_id) count,c.class_id, c.class_name,t1.test_title,AVG(s.score) avg,MAX(s.score)max,MIN(s.score) min from class_student cs,class c,(select * from(select SS.* from score ss,stu_test ut where ss.isdelete='0' and ut.stu_id=ss.user_id and ut.test_id=ss.test_id ORDER BY score desc)t GROUP BY test_id,user_id) s ,test t1 where cs.class_id in( select class_id from class ) and c.class_id=cs.class_id and s.user_id=cs.stu_id and t1.test_id=s.test_id GROUP BY c.class_id,t1.test_title ORDER BY c.create_time,t1.create_time desc LIMIT #{offset},#{limit}")
    List<Map<String,Object>> selectClassList1(@Param("offset") int offset, @Param("limit") int limit);
    /**
     * 查询已完成的试卷
     * @param stuId
     * @return
     */
   @Select("select t.test_title,t.end_time, sub.sub_name,sc.score_id,MAX(sc.score) AS score,sc.test_time,sc.create_time,sc.status,sc.score_number,sc.user_id,sc.test_id,sc.isdelete from \n" +
            " test t inner join stu_test st on t.test_id = st.test_id \n" +
            " inner join user u on u.user_id = st.stu_id \n" +
            " inner join subject sub on sub.sub_id = t.sub_id \n" +
            " inner join (SELECT s.* from score s ORDER BY s.test_time ASC) sc \n" +
            " on sc.test_id = t.test_id AND sc.`user_id`=st.`stu_id` AND st.status =1 and sc.user_id = #{stuId} GROUP BY sc.test_id order by sc.create_time DESC")
    List<ScoreEntity> selectCompleteTestInfos(@Param("stuId") int stuId);
    @Select("SELECT u.user_id,u.user_name,s.score,s.test_time,s.score_number from score s,user u where s.test_id=#{test_id} and s.user_id=#{user_id} and s.score_number=#{count} and s.user_id= u.user_id")
    List<Map<String,Object>> selecttestScore(@Param("user_id") int user_id, @Param("count") int count,@Param("test_id")  int test_id);
    @Update("UPDATE score SET isdelete = '1' WHERE score_id =#{score_id}")
    int updateByScore_id(Integer score_id);
   @Select("select class_id,s.score_id,s.test_id from class_student,score s,test t where class_id=#{class_id} and s.user_id=stu_id and t.test_title=#{test_title} and t.test_id=s.test_id")
    List<Map<String,Object>> selectTestScores(@Param("class_id") int class_id, @Param("test_title") String test_title);
    @Select("select COUNT(*) from (select COUNT(*) from class_student cs,class c,(select * from(select * from score ORDER BY score desc)t GROUP BY test_id,user_id) s ,test t1 where cs.class_id in( select class_id from class) and c.class_id=cs.class_id and s.user_id=cs.stu_id and t1.test_id=s.test_id GROUP BY c.class_id,t1.test_title ORDER BY c.create_time)tt")
    Integer selectClassCount1();
}
