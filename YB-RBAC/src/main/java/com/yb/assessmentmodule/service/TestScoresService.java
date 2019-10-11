package com.yb.assessmentmodule.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/9.
 */
public interface TestScoresService {


    double selectTestScoresCount(int class_id,String test_title);

    Map<String,Object> getTestScoresEdit(int score_id,int class_id);

    int saveScores(int score_id, int score);

    List<Map<String,Object>> selectClassList( int teach_id, int index);

    List<Map<String,Object>> selectTestScoresList(int class_id, String test_title);

    double selectClassCount(int user_id);

    List<Map<String,Object>> selecterorrList(int class_id, String test_title, int index);

    double selecterorrCount(int class_id, String test_title);

    Map<String,List<Map<String, Object>>> lookByTestId(int test_id,int user_id,int count);

    Map<String,Object> selecttestScore(int user_id, int count, int test_id);

    List<Map<String,Object>> selectClassList1(int index);

    int delete(int class_id, String test_title);

    double selectClassCount1();
}
