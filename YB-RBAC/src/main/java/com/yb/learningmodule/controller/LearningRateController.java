package com.yb.learningmodule.controller;

import com.yb.base.pojo.UserEntity;
import com.yb.learningmodule.service.LearningRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/29.
 */
@Controller
@RequestMapping("/learningRate")
public class LearningRateController {
    @Autowired
    private LearningRateService learningRateService;
@RequestMapping("/toLearningRate")

    public  String toLearningRate(HttpServletRequest request, Model model){
    HttpSession session = request.getSession();
    Map<String,Object> user = (Map<String,Object>)session.getAttribute("user");
    UserEntity user1 = (UserEntity) user.get("user");
    int user_id = user1.getUser_id();
    List<Map<String,Object>> list= learningRateService.selectlearningByUserId(user_id);
    Map<String,Object> map=learningRateService.selectRate(user_id);
    model.addAttribute("list",list);
    model.addAttribute("map",map);
    return  "learningRate";
    }
    @RequestMapping("/noLearning")
    public String noLearning(@RequestParam(defaultValue = "1") int  index,int user_id, Model model){

       Integer count= learningRateService.selectCount(user_id);
        List<Map<String, Object>> list=   learningRateService.selectpageList(index,user_id);
       model.addAttribute("count",count);
        model.addAttribute("list",list);
       model.addAttribute("index",index);
     return "noLearningList";
    }

}
