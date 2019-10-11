package com.yb.learningmodule.controller;

import com.yb.base.pojo.TitleTypeEntity;
import com.yb.base.vo.MistakeVo;
import com.yb.learningmodule.service.MistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mayn on 2019/8/28.
 */
@Controller
@RequestMapping("/mistake")
public class MistakeController {
    @Autowired
private MistakeService mistakeService;
    @RequestMapping("/toMistakeList")
    public String selectMistakeList(@RequestParam(defaultValue = "1") int  index,@RequestParam(defaultValue ="") String  type_id,Model model){
       List<MistakeVo> mistakeVo= mistakeService.selectMistakeList(index,type_id);
       double count=mistakeService.selectMistakecount(type_id);
       List<TitleTypeEntity> titleTypeList=mistakeService.selectTitleTypeAll();
       model.addAttribute("titleTypeList",titleTypeList);
       model.addAttribute("mistakeVo",mistakeVo);
        model.addAttribute("type_id",type_id);
        model.addAttribute("index",index);
        if (count>10){
            count=10;
        }
        model.addAttribute("count",count);
        return "mistakeList";
    }
}
