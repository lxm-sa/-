package com.yb.learningmodule.controller;

import com.yb.base.pojo.UserEntity;
import com.yb.base.vo.Resultvo;
import com.yb.learningmodule.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import  com.yb.base.util.VoiceUtil;

/**
 * Created by mayn on 2019/9/2.
 */
@Controller
@RequestMapping("/study")
public class StudyController {
    @Autowired
private StudyService studyService;
    @RequestMapping("/selelctPartShow")
    @Transactional
    public  String selelctPartShow(HttpServletRequest request,String barcode_id, Model model, @RequestParam(defaultValue = "2")int data){

        try {
            String[]str=barcode_id.split(" ");
            char a=barcode_id.charAt(barcode_id.length()-1);
            if (str.length>1){
                barcode_id=str[1];
                barcode_id = barcode_id.substring(0, 12);
            }else {
                barcode_id=str[0];
                if (barcode_id.length()>12){
                    barcode_id = barcode_id.substring(0, 12);
                }

            }
            Map<String,Object>map=studyService.selelctPartShow(barcode_id);
            if(map.size()>3){
                HttpSession session = request.getSession();
                Map<String,Object> user = (Map<String,Object>)session.getAttribute("user");
                UserEntity user1 = (UserEntity) user.get("user");
                int user_id = user1.getUser_id();
                int number=studyService.insertStudy(user_id,barcode_id);
            }
            model.addAttribute("map",map);
            model.addAttribute("data",data);
            return "partShow";
        }catch (Exception e){
            model.addAttribute("msg2","没有该条形码或者扫描出错");
            return  "forward:/study/firstPage";
        }
    }
    @RequestMapping("/firstPage")
    public  String firstPage(){
            return "firstPage";
    }
//    @RequestMapping("/voice")
//    public  Resultvo getvoice() throws IOException {
//        Thread.interrupted();
//        VoiceUtil voiceUtil= new VoiceUtil();
//        Resultvo resultvo = new Resultvo();
//            voiceUtil.voice();
//            resultvo.setMsg( "hh");
//            return resultvo;
//    }
}
