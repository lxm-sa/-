package com.yb.assessmentmodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.assessmentmodule.service.TestScoresService;
import com.yb.base.mapper.ClassStudentMapper;
import com.yb.base.mapper.ScoreMapper;
import com.yb.base.mapper.StuAnswerMapper;
import com.yb.base.mapper.TestMapper;
import com.yb.base.pojo.ScoreEntity;
import com.yb.base.pojo.StuAnswerEntity;
import com.yb.base.pojo.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mayn on 2019/9/9.
 */
@Service
public class TestScoresServiceImpl implements TestScoresService{
    @Autowired
    private ScoreMapper scoresMapper;
    @Autowired
    private ClassStudentMapper classStudentMapper;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private StuAnswerMapper stuAnswerMapper;
    @Override
    public List<Map<String, Object>> selectTestScoresList(int class_id,String test_title) {
        List<Map<String, Object>> list=scoresMapper.selectTestScoresList(class_id,test_title);
        return list;
    }

    @Override
    public double selectClassCount(int user_id) {
        Integer countnumber = scoresMapper.selectClassCount(user_id);
        double countpage=Math.ceil(countnumber/8.0);
        if(countnumber==0){
            countpage=1.0;
        }
        return countpage;
    }

    @Override
    public List<Map<String, Object>> selecterorrList(int class_id, String test_title, int index) {
        List<Map<String, Object>> list=stuAnswerMapper.selecterorr(class_id,test_title,(index-1)*8,8);
        List<Map<String,Object>> list1=new ArrayList<>();
        Map<String, Object> map5 = new HashMap<>();
        for(Map<String,Object> map1:list) {
            Map<String, Object> map2 = new HashMap<>();
            if (map5.get(map1.get("title_id")+"")==null) {
                for (Map<String, Object> map3 : list) {
                    if ((map1.get("title_id")).equals(map3.get("title_id"))) {
                        if (map2.get("title_id") != null) {
                            String content_name = (String) map2.get("part_name");
                            String content_name1 = (String) map3.get("part_name");
                            map2.put("part_name", content_name + "," + content_name1);
                        } else {
                            map2.put("part_name", (String) map3.get("part_name"));
                            map2.put("title_type_name", (String) map3.get("title_type_name"));
                            map2.put("count1", (Long) map3.get("count1"));
                            map2.put("error", (Long) map3.get("error"));
                            map2.put("title_content", (String) map3.get("title_content"));
                            map2.put("title_id", (Integer) map3.get("title_id"));
                            String rate=((double)(Long) map3.get("error")/(Long) map3.get("count1")*100.00)+"%";
                            map2.put("rate",rate);
                            map5.put(map1.get("title_id") + "", (Integer) map3.get("title_id"));
                        }
                    }
                }
                list1.add(map2);
            }else {
                continue;
            }

        }
        return list1;
    }

    @Override
    public double selecterorrCount(int class_id, String test_title) {
        Integer integer=stuAnswerMapper.selecterorrCount(class_id,test_title);
        double count1 =Math.ceil(integer/8.0);
        if (count1==0){
            count1=1;
        }
        return count1;

    }

    @Override
    public Map<String,List<Map<String, Object>>> lookByTestId(int test_id,int user_id,int count) {
        TestEntity testEntity = testMapper.selectById(test_id);
        Map<String,List<Map<String, Object>>> map=new HashMap<>();
         List<Map <String,Object>> list=testMapper.selectTestById(test_id);

         List<Map<String, Object>> list1 = new ArrayList<>();
        Map<String, Object> map5 = new HashMap<>();

        for(Map<String,Object> map1:list) {
            Map<String, Object> map2 = new HashMap<>();
            if (map5.get(map1.get("title_id")+"")==null) {
                for (Map<String, Object> map3 : list) {
                    if ((map1.get("title_id")).equals(map3.get("title_id"))) {
                        if (map2.get("title_id") != null) {
                            String content_name = (String) map2.get("part_name");
                            String content_name1 = (String) map3.get("barcode_id")+":"+(String) map3.get("part_name");
                            map2.put("part_name", content_name + ";" + content_name1);
                        } else {
                            map2.put("part_name", (String) map3.get("barcode_id")+":"+(String) map3.get("part_name"));
                            map2.put("title_id", (Integer) map3.get("title_id"));
                            map2.put("barcode_id", (String) map3.get("barcode_id"));
                            map2.put("title_content", (String) map3.get("title_content"));
                            map5.put(map1.get("title_id") + "", (Integer) map3.get("title_id"));
                        }
                    }
                }
                list1.add(map2);
            }else {
                continue;
            }

        }
        List<Map<String, Object>> list3 = new ArrayList<>();
        for(Map<String,Object> map12:list1) {
            Map<String, Object> map2 = new HashMap<>();
            Wrapper wrapper= new EntityWrapper();
            wrapper.eq("user_id",user_id);
            wrapper.eq("count",count);
            wrapper.eq("test_id",test_id);
            wrapper.eq("title_id",(Integer) map5.get("" + (Integer) map12.get("title_id")));
            List<StuAnswerEntity> list4 = stuAnswerMapper.selectList(wrapper);
            if (list4.size()==0) {
                map2.put("part_name", "未填");
                map2.put("title_id", (Integer) map12.get("title_id"));
                map2.put("stu_answer_barcode", "");
                map2.put("status", 0);
                list3.add(map2);
                continue;
            }
            StuAnswerEntity stuAnswerEntity= (StuAnswerEntity)list4.get(0);
            if (stuAnswerEntity.getStatus()==0) {
                map2.put("part_name", "未填");
                map2.put("title_id", (Integer) map12.get("title_id"));
                map2.put("stu_answer_barcode", "");
                map2.put("status", 0);
                list3.add(map2);
                continue;
            }
            Map map22 = new HashMap();
            List<Map<String, Object>> list2 = stuAnswerMapper.selectStuAnswer(stuAnswerEntity.getStu_answer_id());
                    if (list2.size() == 0) {
                        map2.put("part_name", stuAnswerEntity.getStu_answer_barcode()+":"+"(没有该部件)");
                        map2.put("title_id", (Integer) map12.get("title_id"));
                        map2.put("stu_answer_barcode",stuAnswerEntity.getStu_answer_barcode());
                        map2.put("status", 1);
                        list3.add(map2);
                    }else{
                        for (Map<String, Object> maplist2:list2) {
                            map2.put("part_name", stuAnswerEntity.getStu_answer_barcode()+":"+(String)maplist2.get("part_name"));
                            map2.put("title_id", (Integer) maplist2.get("title_id"));
                            map2.put("stu_answer_barcode", stuAnswerEntity.getStu_answer_barcode());
                            map2.put("status", (Integer) maplist2.get("status"));
                        }

                        list3.add(map2);
                       }
            }
            //

         map.put("list",list1);
        map.put("list1",list3);
        return map;
    }

    @Override
    public Map<String, Object> selecttestScore(int user_id, int count, int test_id) {
        List<Map<String, Object>> list=scoresMapper.selecttestScore(user_id,count,test_id);
        Map<String,Object> map=new HashMap<>();
        if (list.size()>0){
            map=list.get(0);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> selectClassList1(int index) {
        List<Map<String, Object>> list= scoresMapper.selectClassList1((index-1)*8,8);
        List<Map<String, Object>> list1=new ArrayList<>();
        for(Map<String, Object> map:list){
            Integer class_id = (Integer) map.get("class_id");
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("class_id",class_id);
            Integer count1 = classStudentMapper.selectCount(wrapper);
            map.put("count1",count1);
            list1.add(map);
        }
        return list1;
    }

    @Override
    public int delete(int class_id, String test_title) {
        List<Map<String, Object>> list=scoresMapper.selectTestScores(class_id,test_title);
        int number=0;
        for (Map<String, Object> map:list){
            Integer score_id = (Integer) map.get("score_id");
             number=scoresMapper.updateByScore_id(score_id);
        }
        return number;
    }

    @Override
    public double selectClassCount1() {
        Integer countnumber =  scoresMapper.selectClassCount1();
        double countpage=Math.ceil(countnumber/8.0);
        if(countnumber==0){
            countpage=1.0;
        }
        return countpage;
    }

    @Override
    public double selectTestScoresCount(int class_id,String test_title) {
        Integer countnumber = scoresMapper.selectTestScoresCount(class_id,test_title);
        double countpage=Math.ceil(countnumber/8.0);
        if(countnumber==0){
            countpage=1.0;
        }
        return countpage;
    }

    @Override
    public Map<String, Object> getTestScoresEdit(int score_id,int class_id) {
        Map<String, Object> map= scoresMapper.selectTestScoresEdit(score_id,class_id);
        return map;
    }

    @Override
    public int saveScores(int score_id, int score) {
        ScoreEntity scoreEntity=scoresMapper.selectById(score_id);
        scoreEntity.setScore_id(score_id);
        scoreEntity.setScore(score);
        scoreEntity.setUpdate_time(new Date());
        Integer integer = scoresMapper.updateById(scoreEntity);
        return integer;
    }

    @Override
    public List<Map<String, Object>> selectClassList(int teach_id, int index) {
        List<Map<String, Object>> list= scoresMapper.selectClassList(teach_id,(index-1)*8,8);
        List<Map<String, Object>> list1=new ArrayList<>();
        for(Map<String, Object> map:list){
            Integer class_id = (Integer) map.get("class_id");
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("class_id",class_id);
            Integer count1 = classStudentMapper.selectCount(wrapper);
            map.put("count1",count1);
            list1.add(map);
        }
        return list1;
    }
}
