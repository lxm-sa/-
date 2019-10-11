package com.yb.learningmodule.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/29.
 */
public interface LearningRateService {
    List<Map<String,Object>> selectlearningByUserId(int user_id);

    Map<String,Object> selectRate(int user_id);

    Integer selectCount(int user_id);

    List<Map<String, Object>> selectpageList(int index, int user_id);
}
