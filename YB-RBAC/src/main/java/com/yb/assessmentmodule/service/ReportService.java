package com.yb.assessmentmodule.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/12.
 */
public interface ReportService {
    List<Map<String,Object>> selectTestScoresList(int class_id, String test_title);
}
