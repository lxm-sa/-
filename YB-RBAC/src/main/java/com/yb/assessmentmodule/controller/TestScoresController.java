package com.yb.assessmentmodule.controller;

import com.yb.assessmentmodule.service.TestScoresService;
import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.base.vo.RolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/9.
 */
@Controller
@RequestMapping("/testScores")
public class TestScoresController {
    @Autowired
    private TestScoresService testScoresService;
    @RequestMapping("/to_list")
    public String getClassList(HttpServletRequest request,@RequestParam(defaultValue="1")int index, Model model){
        HttpSession session = request.getSession();
        Map<String,Object> user = (Map<String,Object>)session.getAttribute("user");
        UserEntity user1 = (UserEntity) user.get("user");
        RoleEntity roleEntity = (RoleEntity) user.get("role");
        int user_id = user1.getUser_id();
        List<Map<String,Object>> list=null;
        double count=0;
        if (roleEntity.getRole_id()==10003){
             list= testScoresService.selectClassList(user_id,index);
            count=testScoresService.selectClassCount(user_id);
        }else if (roleEntity.getRole_id()==10002||roleEntity.getRole_id()==10004){
            list= testScoresService.selectClassList1(index);
            count=testScoresService.selectClassCount1();
        }



        model.addAttribute("list",list);
        model.addAttribute("count",count);
        model.addAttribute("index",index);
        return "classList";
    }
    @RequestMapping("/to_studentList")
    public String getTestScoresList(String test_title,int class_id ,Model model){
        List<Map<String,Object>> list= testScoresService.selectTestScoresList(class_id,test_title);
        Map map= new HashMap();
        map.put("test_title",test_title);
        map.put("class_id",class_id);
        model.addAttribute("list",list);
        model.addAttribute("map",map);
        return "testScoresList";
    }
    @RequestMapping("/to_erorrList")
    public String getrorrList(@RequestParam(defaultValue="1")int index,String test_title,int class_id ,Model model){
        List<Map<String,Object>> list= testScoresService.selecterorrList(class_id,test_title,index);
        double count=testScoresService.selecterorrCount(class_id,test_title);
        model.addAttribute("list",list);
        model.addAttribute("count",count);
        model.addAttribute("index",index);
        return "errorList";
    }
    @RequestMapping("/to_edit")
    public String getTestScoresEdit(int score_id,int class_id, Model model){
        Map<String,Object> map= testScoresService.getTestScoresEdit(score_id,class_id);
        model.addAttribute("map",map);
        return "scoreEdit";
    }
    @RequestMapping("/saveScores")
    @Transactional
    public String saveScores(int score_id,int score,Model model){
        try {
            int number= testScoresService.saveScores(score_id,score);
            if (number==1){
                model.addAttribute("msg1","成绩修改成功！");
                return "forward:/testScores/to_edit";
            }else {
                model.addAttribute("msg2","成绩修改失败！");
                return "forward:/testScores/to_edit";
            }
        }catch (Exception e){
            model.addAttribute("msg2","成绩修改失败！");
            return "forward:/testScores/to_edit";
        }

    }
    @RequestMapping("/to_del")
    @Transactional
    public String delete(int class_id,String test_title,Model model){
        try {
            int number= testScoresService.delete(class_id,test_title);
            if (number==1){
                model.addAttribute("msg1","成绩删除成功！");
                return "forward:/testScores/to_list";
            }else {
                model.addAttribute("msg2","成绩删除失败！");
                return "forward:/testScores/to_list";
            }
        }catch (Exception e){
            model.addAttribute("msg2","成绩删除失败！");
            return "forward:/testScores/to_list";
        }

    }
    @RequestMapping("/lookByTestId")
    public String lookByTestId(int test_id,int user_id,int count,String test_title,int class_id , Model model){
        Map<String,List<Map<String, Object>>>  map= testScoresService.lookByTestId(test_id,user_id, count);
          Map<String,Object> map1=testScoresService.selecttestScore(user_id,count,test_id);
        model.addAttribute("map",map);
        model.addAttribute("map1",map1);
        model.addAttribute("test_title",test_title);
        model.addAttribute("class_id",class_id);
        return "testLook";
    }
}
