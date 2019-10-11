package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.StuAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/28.
 */
@Mapper
public interface StuAnswerMapper  extends BaseMapper<StuAnswerEntity>{
    @Select("select s.title_id,t.title_content,t.title_type_id  from (select *,COUNT(title_id) count1 from stu_answer stu WHERE  stu.status NOT in (2) GROUP BY title_id)s,title t where t.title_id=s.title_id and title_type_id like CONCAT('%',#{title_type_id},'%')  ORDER BY s.count1 desc  LIMIT #{offset},#{limit}")
    List<Map<String,Object>> selectAllpage(@Param("offset") int offset, @Param("limit") int limit, @Param("title_type_id") String title_type_id);
@Select("select COUNT(count) from (select COUNT(title_id) count from stu_answer stu WHERE  stu.status NOT in (2) and stu.title_id in (select title_id from title where title_type_id like CONCAT('%',#{title_type_id},'%'))  GROUP BY title_id)s where s.count>5")
    int selectMistakecount(String title_type_id);
    @Select("select ttt.*,p.part_name,tty.title_type_name  from answer an,parts p,title_type tty, (select COUNT(sa.title_id) count1,(SELECT COUNT(stu_answer_id) from stu_answer where sa.user_id=user_id and title_id=sa.title_id and status not in (2))error,s.test_id,s.user_id,title.title_content,sa.title_id,title.title_type_id from test t,score s,class_student cs,stu_answer sa,title where title.title_id=sa.title_id and  t.test_title=#{test_title} and s.test_id=t.test_id and cs.class_id =#{class_id} and cs.stu_id=s.user_id and sa.test_id=s.test_id and sa.user_id=s.user_id  GROUP BY sa.title_id order by error)ttt where an.title_id=ttt.title_id and p.part_id=an.part_id and ttt.title_type_id=tty.title_type_id limit #{offset},#{limit}")
    List<Map<String,Object>> selecterorr(@Param("class_id") int class_id,@Param("test_title")String test_title,@Param("offset") int offset, @Param("limit") int limit);
    @Select("select COUNT(title_id) from (select s.test_id,s.user_id,sa.title_id from test t,score s,class_student cs,stu_answer sa where  t.test_title=#{test_title} and s.test_id=t.test_id and cs.class_id ='101' and cs.stu_id=s.user_id and sa.test_id=s.test_id and sa.user_id=s.user_id  GROUP BY sa.title_id )tt")
    Integer selecterorrCount(@Param("class_id") int class_id,@Param("test_title")String test_title);
    @Select("select * from stu_answer where user_id = #{stuId} and test_id = #{testId} and title_id = #{titleId} and count=#{count}")
    StuAnswerEntity selectOneByMap(@Param("stuId")int stuId, @Param("testId")int testId, @Param("titleId")int titleId,@Param("count")int count);

    @Select("select * from stu_answer where user_id = #{userId} and test_id = #{testId} and status = 2 and count=#{count}")
    List<StuAnswerEntity> selectTrueAnswerByMap(@Param("userId")int userId, @Param("testId")int testId,@Param("count")int count);

    @Select("select sa.*,t.title_content from stu_answer sa, title t where sa.title_id = t.title_id and sa.user_id = #{userId} and sa.test_id = #{testId} and sa.count=#{count}")
    List<StuAnswerEntity> selectAnswerListByMap(@Param("userId")int userId, @Param("testId")int testId,@Param("count")int count);


    @Select("select p.part_id,p.part_name from stu_answer sa,barcode b, parts p where sa.stu_answer_barcode = b.barcode_id and b.part_id = p.part_id and " +
            " sa.stu_answer_id = #{stuAnswerId}")
    StuAnswerEntity selectAnswerByMap(@Param("stuAnswerId")int stuAnswerId);
    @Select("select * from barcode b,parts p where barcode_id=#{barcode_id} and b.part_id=p.part_id")
    List<Map<String,Object>> selectStuAnswer(@Param("barcode_id")int barcode_id);
}
