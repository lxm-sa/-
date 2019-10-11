package com.yb.managemodule.controller;

import com.yb.base.pojo.ModularEntity;
import com.yb.base.pojo.PermissionEntity;
import com.yb.base.vo.PermissionVo;
import com.yb.managemodule.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/24.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/toPermissionList")
    public String getRoleList(@RequestParam(defaultValue = "1") int index, Model model) {
        List<PermissionVo> perList = permissionService.selectPerList(index);
        double count = permissionService.selectPerCount();
        model.addAttribute("perList", perList);
        model.addAttribute("count", count);
        model.addAttribute("index", index);
        return "permissionList";
    }
    @RequestMapping("/toPermissionAdd")
    @Transactional
    public String permissionAdd( Model model) {
       List<ModularEntity> modularList=  permissionService.selectModular();
      List<PermissionEntity> Permission_parentList=permissionService.selectPermission_parent();
        model.addAttribute("modularList", modularList);
        model.addAttribute("Permission_parentList",Permission_parentList);
        return "permissionAdd";
    }

    @RequestMapping("/addPermission")
    @Transactional
    public String addPermission(PermissionEntity permissionEntity, Model model) {
        try {
            int number=  permissionService.addPermission(permissionEntity);
            if (number > 0) {
                model.addAttribute("msg1", "添加成功！");
                return  "forward:/permission/toPermissionAdd";
            } else {
                model.addAttribute("msg2", "添加失败！");
                return  "forward:/permission/toPermissionAdd";
            }

        }catch (Exception e){
            model.addAttribute("msg2", "添加失败！");
            return  "forward:/permission/toPermissionAdd";
        }
    }
    @RequestMapping("/savePermission")
    @Transactional
    public String savePermission(PermissionEntity permissionEntity, Model model) {
        try {
            int number=  permissionService.savePermission(permissionEntity);
            if (number > 0) {
                model.addAttribute("msg1", "保存成功！");
                return  "forward:/permission/toPermissionEdit";
            } else {
                model.addAttribute("msg2", "保存失败！");
                return  "forward:/permission/toPermissionEdit";
            }

        }catch (Exception e){
            model.addAttribute("msg2", "添加失败！");
            return  "forward:/permission/toPermissionEdit";
        }
    }
    @RequestMapping("/deleteCheckPermissions")
    @Transactional
    public  String deleteCheckPermissions(Integer[] permissionId, Model model){
        int number=permissionService.deleteCheckPermissions(permissionId);
        if(number>0){
            model.addAttribute("msg1","删除权限成功！");
            return "forward:/permission/toPermissionList";
        }else {
            model.addAttribute("msg2","删除权限失败！");
            return "forward:/permission/toPermissionList";
        }

    }

    @RequestMapping("/selectPermission_name")
    public  String selectPermission_name(String permission_name,@RequestParam(defaultValue = "1") int index, Model model){
        List<PermissionVo> PermissionVo= permissionService.selectPermission_name(permission_name);
        model.addAttribute("perList", PermissionVo);
        model.addAttribute("count", 1);
        model.addAttribute("index", 1);
        return "permissionList";
    }

    @RequestMapping("/toPermissionEdit")
    public  String toPermissionEdit(Integer permission_id, Model model){
        try {
            Map<String, Object> map = permissionService.toPermissionEdit(permission_id);
            model.addAttribute("map", map);
            return  "permissionEdit";
        }catch (Exception e){
            return  "forward:/permission/toPermissionList";
        }
    }
    @RequestMapping("/deletePermissionById")
    @Transactional
    public  String deletePermissionById(Integer permission_id,Model model){
        try {

            int number = permissionService.deletePermissionById(permission_id);
            if (number > 0) {
                model.addAttribute("msg1", "删除权限成功！");
                return  "forward:/permission/toPermissionList";
            } else {
                model.addAttribute("msg2", "删除权限失败！");
                return  "forward:/permission/toPermissionList";
            }

        }catch (Exception e){
            return  "forward:/permission/toPermissionList";
        }
    }
}
