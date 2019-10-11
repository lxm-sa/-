package com.yb.loginmodule.controller;


import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.loginmodule.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class loginController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(value="/toLogin")
    public String toLogin(HttpServletRequest request) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping(value="/toIndex")
    public String toIndex(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
            UserEntity user =(UserEntity) usermap.get("user");
            if(user.getStatus()==0){
                request.setAttribute("msg", "用户审核未通过，如有意见，请联系管理员");
                return "forward:/toLogin";
            }if(user.getStatus()==2){
                request.setAttribute("msg", "用户待审核状态，如果时间紧急，请联系管理员");
                return "forward:/toLogin";
            }if(user.getIsdelete()==1){
                request.setAttribute("msg", "该账号已被锁定，请联系管理员");
                return "forward:/toLogin";
            }
            SecurityUtils.getSubject().getSession().setTimeout(120*60*1000);

            return "index";
        }catch (Exception e){
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return "forward:/toLogin";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }
    @RequestMapping("/register")
    public String register(Model model) {
        List<RoleEntity> list = userService.selectAllRole();
        model.addAttribute("roles",list);
        return "register";
    }
}
