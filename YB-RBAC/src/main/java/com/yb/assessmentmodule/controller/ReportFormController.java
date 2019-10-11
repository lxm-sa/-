package com.yb.assessmentmodule.controller;


import com.yb.assessmentmodule.service.ReportService;
import com.yb.base.util.ExcelUtil;
import com.yb.base.vo.Resultvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/12.
 */
@Controller
@RequestMapping("/report")
public class ReportFormController{
    @Autowired
 private ReportService reportService;
    @RequestMapping("/ReportExcel")
    @ResponseBody
    private Resultvo ReportExcel(String test_title, int class_id , Model model) throws Exception{
        Resultvo resultvo = new Resultvo();
            List<Map<String,Object>> list= reportService.selectTestScoresList(class_id,test_title);
            String[] strArray = { "排名","学号", "姓名","成绩","考试时长","考试日期"};
            String[] keys = { "index","user_id", "user_name","score","test_time","create_time"};
            String url = ExcelUtil.createExcel(list, strArray, keys);
            resultvo.setMsg(url);
            resultvo.setStatus(200);
            return resultvo;


    }





}