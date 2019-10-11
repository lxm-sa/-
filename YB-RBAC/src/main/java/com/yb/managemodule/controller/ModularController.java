package com.yb.managemodule.controller;

import com.yb.base.pojo.ModularEntity;
import com.yb.managemodule.service.ModularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mayn on 2019/8/26.
 */
@Controller
@RequestMapping("/modular")
public class ModularController {
    @Autowired
private ModularService modularService;
    @RequestMapping("/toModularList")
    public String toModularList(@RequestParam(defaultValue = "1") int index, Model model) {
         List<ModularEntity> modularList=modularService.toModularList(index);
        double count = modularService.selectModularCount();
        model.addAttribute("modularList", modularList);
        model.addAttribute("count", count);
        model.addAttribute("index", index);
        return "modularList";
    }
    @RequestMapping("/toModularAdd")
    public String modularAdd( Model model) {
        return "modularAdd";
    }

    @RequestMapping("/addModular")
    @Transactional
    public String addModular(ModularEntity  modularEntity, Model model) {
        try {
            int number=  modularService.addModular(modularEntity);
            if (number > 0) {
                model.addAttribute("msg1", "添加成功！");
                return  "forward:/modular/toModularList";
            } else {
                model.addAttribute("msg2", "添加失败！");
                return  "forward:/modular/toModularAdd";
            }

        }catch (Exception e){
            model.addAttribute("msg2", "添加失败！");
            return  "forward:/modular/toModularAdd";
        }
    }
    @RequestMapping("/selectModular_name")
    public  String selectModular_name(String modular_name,@RequestParam(defaultValue = "1") int index, Model model){
        List<ModularEntity> modularList= modularService.selectModular_name(modular_name);
        model.addAttribute("modularList", modularList);
        model.addAttribute("count", 1);
        model.addAttribute("index", 1);
        return "modularList";
    }

    @RequestMapping("/deleteCheckModulars")
    @Transactional
    public  String deleteCheckModulars(Integer[] modularId, Model model){
        int number=modularService.deleteCheckModulars(modularId);
        if(number>0){
            model.addAttribute("msg1","删除模块成功！");
            return "forward:/modular/toModularList";
        }else {
            model.addAttribute("msg2","删除模块失败！");
            return "forward:/modular/toModularList";
        }
    }
    @RequestMapping("/toModularDeleteById")
    @Transactional
    public  String toModularDeleteById(Integer modular_id,Model model){
        try {

            int number = modularService.toModularDeleteById(modular_id);
            if (number > 0) {
                model.addAttribute("msg1", "删除权限成功！");
                return  "forward:/modular/toModularList";
            } else {
                model.addAttribute("msg2", "删除权限失败！");
                return  "forward:/modular/toModularList";
            }

        }catch (Exception e){
            return  "forward:/modular/toModularList";
        }
    }
    @RequestMapping("/toModularEdit")
    public  String toModularEdit(Integer modular_id, Model model){
        try {
            ModularEntity modularEntity= modularService.toModularEdit(modular_id);
            model.addAttribute("modular",modularEntity);
            return  "modularEdit";
        }catch (Exception e){
            return  "forward:/modular/toModularList";
        }
    }
    @RequestMapping("/saveModular")
    public  String saveModular(ModularEntity modularEntity, Model model){
        try {
            Integer number= modularService.saveModular(modularEntity);
            if (number>0) {
                model.addAttribute("msg1", "修改成功");
                return "forward:/modular/toModularList";
            }else {
                model.addAttribute("msg1", "修改失败");
                return  "forward:/modular/toModularEdit?modular_id="+modularEntity.getModular_id();
            }
        }catch (Exception e){
            model.addAttribute("msg1", "修改失败");
            return  "forward:/modular/toModularEdit?modular_id="+modularEntity.getModular_id();
        }
    }
}
