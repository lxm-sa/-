package com.yb.assessmentmodule.service.impl;

import com.yb.assessmentmodule.service.ReportService;
import com.yb.base.mapper.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/12.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ScoreMapper scoresMapper;
    @Override
    public List<Map<String, Object>> selectTestScoresList(int class_id, String test_title) {
        List<Map<String, Object>> list = scoresMapper.selectTestScoresList(class_id, test_title);
        int index=1;
        for (Map<String, Object> map :list){
            map.put("index",index);
            index++;
        }
        return list;
    }
}
