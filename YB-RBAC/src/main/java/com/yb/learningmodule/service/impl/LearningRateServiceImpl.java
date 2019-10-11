package com.yb.learningmodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.BarcodeMapper;
import com.yb.base.mapper.StudyMapper;
import com.yb.learningmodule.service.LearningRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/29.
 */
@Service
public class LearningRateServiceImpl implements LearningRateService{
    @Autowired
    private StudyMapper studyMapper;
    @Autowired
    private BarcodeMapper barcodeMapper;
    @Override
    public List<Map<String,Object>> selectlearningByUserId(int user_id) {
        List<Map<String,Object>> list=studyMapper.selectlearningByUserId(user_id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Map map:list){
            Date create_time =(Timestamp) map.get("create_time");
            String format = simpleDateFormat.format(create_time);
            map.put("create_time",format);
        }
        return list;
    }

    @Override
    public Map<String, Object> selectRate(int user_id) {
        Map<String, Object> map=new HashMap<>();
        Wrapper wrapper= new EntityWrapper();
        wrapper.eq("user_id",user_id);
        double count1 = studyMapper.selectCount(wrapper);

        map.put("studyCount",count1);
        double count2 = barcodeMapper.selectCount(new EntityWrapper<>());
        map.put("barcodeCount",count2);
        map.put("count",new java.text.DecimalFormat("0.00").format(count1/count2*100));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        map.put("date",format);

        return map;
    }

    @Override
    public Integer selectCount(int user_id) {
        Map<String, Object> map = selectRate(user_id);
        double studyCount = (double)map.get("studyCount");
        double barcodeCount =(double) map.get("barcodeCount");
        Integer count=(int)Math.ceil((barcodeCount-studyCount)/8.0);
        if (count==0){
            count=1;
        }
        return count;
    }

    @Override
    public  List<Map<String, Object>> selectpageList(int index, int user_id) {
        List<Map<String, Object>> list = studyMapper.selectpageList((index - 1) * 8, 8, user_id);
        return list;
    }
}
