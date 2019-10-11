package com.yb.learningmodule.service;

import java.util.Map;

/**
 * Created by mayn on 2019/9/2.
 */
public interface StudyService {
    Map<String,Object> selelctPartShow(String barcode_id);

    int insertStudy(int user_id,String barcode_id);
}
