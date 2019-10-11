package com.yb.managemodule.controller;

import com.yb.base.pojo.RoleEntity;
import com.yb.base.vo.ModularVo;
import com.yb.base.vo.Resultvo;
import com.yb.base.vo.RolePermissionVo;
import com.yb.managemodule.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/21.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleManageService roleManageService;
    @RequestMapping("toRoleList")
public  String getRoleList(@RequestParam(defaultValue="1")int index, Model model){
     List<RolePermissionVo> rolePervos= roleManageService.selectRoleList(index);
        double count=roleManageService.selectRoleCount();
     model.addAttribute("roles",rolePervos);
     model.addAttribute("count",count);
     model.addAttribute("index",index);
    return "roleList";
}
    @RequestMapping("/selectRole_name")
    public  String selectRoleName(String role_name,Model model){
        List<RolePermissionVo> rolePervos= roleManageService.selectRoleName(role_name);
        model.addAttribute("roles",rolePervos);
        model.addAttribute("count", 1);
        model.addAttribute("index", 1);
        return "roleList";
    }
    @RequestMapping("/deleteCheckRoles")
    @Transactional
    public  String deleteCheckRoles(Integer[] roleId, Model model){
        int number=roleManageService.deleteRoles(roleId);
        if(number>0){
            model.addAttribute("msg1","删除角色成功！");
            return "forward:/role/toRoleList";
        }else {
            model.addAttribute("msg2","删除角色失败！");
            return "forward:/role/toRoleList";
        }

    }

    @RequestMapping("/toRoleAdd")
    public  String toRoleAdd(Model model){
         List<ModularVo> modularVos=roleManageService.getRoleAdd();
         model.addAttribute("modularVos",modularVos);
        return  "roleAdd";
    }
    @RequestMapping("/addRole")
    @Transactional
    public  String addRole(String role_name,Integer[]permissionId, Model model){
        try {
            int number = roleManageService.addRole(role_name,permissionId);
            if (number > 0) {
                model.addAttribute("msg1", "添加成功！");
                return  "forward:/role/toRoleAdd";
            } else {
                model.addAttribute("msg2", "添加失败！");
                return  "forward:/role/toRoleAdd";
            }

        }catch (Exception e){
            model.addAttribute("msg2", "添加失败！");
            return  "forward:/role/toRoleAdd";
        }
    }
    @RequestMapping("/roleName")
    @ResponseBody
    public Resultvo getaccountName(String role_name) {
        List<RoleEntity> roles = roleManageService.selectGetRoleName(role_name);
        Resultvo resultvo = new Resultvo();

        if (roles.size()!=0) {
            resultvo.setMsg( "角色名已存在！请重新输入");
        }
        return resultvo;
    }
    @RequestMapping("/toRoleEdit")
    @Transactional
    public  String toRoleEdit(Integer role_id, Model model){
        try {
            Map<String, Object> map = roleManageService.toRoleEdit(role_id);
                model.addAttribute("map", map);
                return  "roleEdit";
        }catch (Exception e){
            return  "forward:/role/toRoleList";
        }
    }
    @RequestMapping("/deleteRoleById")
    @Transactional
    public  String deleteRoleById(Integer role_id,Model model){
        try {

            int number = roleManageService.deleteRoleById(role_id);
            if (number > 0) {
               model.addAttribute("msg1", "删除角色成功！");
               return  "forward:/role/toRoleList";
           } else {
               model.addAttribute("msg2", "删除角色失败！");
              return  "forward:/role/toRoleList";
          }

        }catch (Exception e){
            return  "forward:/role/toRoleList";
        }
    }
    @RequestMapping("/alterRoleName")
    @ResponseBody
    public Resultvo alterRoleName( String role_name,int role_id) {
        List users = roleManageService.alterRoleName(role_name,role_id);
        Resultvo resultvo = new Resultvo();

        if (users.size()!=0) {
            resultvo.setMsg( "账号名已被注册！请重新输入");

        }
        return resultvo;
    }

    @RequestMapping("/saveEditRole")
    @Transactional
    public  String saveEditRole(String role_name,int role_id,Integer[]permissionId, Model model){
        try {
            int number = roleManageService.saveEditRole(role_name,role_id,permissionId);
            if (number > 0) {
                model.addAttribute("msg1", "保存成功！");
                return  "forward:/role/toRoleList";
            } else {
                model.addAttribute("msg2", "保存失败！");
                return  "forward:/role/toRoleAdd";
            }

        }catch (Exception e){
            model.addAttribute("msg2", "添加失败！");
            return  "forward:/role/toRoleAdd";
        }
    }
}

