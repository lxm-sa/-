package com.yb.loginmodule.controller;

import com.yb.base.pojo.UserEntity;
import com.yb.base.pojo.UserRoleRefEntity;
import com.yb.base.util.Md5Utils;
import com.yb.base.vo.Resultvo;
import com.yb.loginmodule.service.RoleService;
import com.yb.loginmodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/3.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
private UserService userService;
    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "/loginUser" )
    public String UserLogin(HttpServletRequest request){
        try {
            Object loginFailure = request.getAttribute("shiroLoginFailure");
            if (loginFailure!=null){
                request.setAttribute("msg", "用户名或者密码不正确，请重新输入");
                return "forward:/toLogin";
            }

            return "forward:/toIndex";
        }catch (Exception e){
            return "forward:/toLogin";
        }

    }
    @RequestMapping("/toHome")
    public String toHome(){
        return "home";
    }

    @RequestMapping("/register")
    @Transactional
    public String register(UserEntity user, String role_id, Model model) {
        try {
            int number = userService.addUser(user);
            UserRoleRefEntity userRoleRefEntity = new UserRoleRefEntity();
            userRoleRefEntity.setRole_id(Integer.parseInt(role_id));
            userRoleRefEntity.setUser_id(user.getUser_id());
            int number1 = roleService.addRole(userRoleRefEntity);
            if (number == 1 && number1 == 1) {
                model.addAttribute("msg1", "注册成功");
                return "register";
            } else {
                model.addAttribute("msg2", "注册失败,请重新注册");
                return "forward:/register";
            }
        }catch (Exception e){
            model.addAttribute("msg2", "注册失败");
            return "forward:/register";
        }
    }

    @RequestMapping("/accountName")
    @ResponseBody
    public Resultvo getaccountName(String accountName) {
        List users = userService.selectAccountName(accountName);
        Resultvo resultvo = new Resultvo();

          if (users.size()!=0) {
              resultvo.setMsg( "账号名已被注册！请重新输入");

          }
            return resultvo;
    }
   @RequestMapping("/alter")
    public  String userAlter(Model model){
        return "alter";
    }
    @RequestMapping("/alterAccountName")
    @ResponseBody
    public Resultvo getalterAccountName( String account_name,String user_id) {
        int id=Integer.parseInt(user_id);
        List users = userService.selectalterAccountName(account_name,id);
        Resultvo resultvo = new Resultvo();

        if (users.size()!=0) {
            resultvo.setMsg( "账号名已被注册！请重新输入");

        }
        return resultvo;
    }
    @RequestMapping("/saveAlter")
    @Transactional
    public String saveAlter(HttpServletRequest request,UserEntity user,Model model) {
        try {
            int number = userService.saveuser(user);
            if (number == 1 ) {
                Map<String, Object> user1 = userService.authcUser(user.getAccount_name());
                HttpSession session = request.getSession();
                 session.setAttribute("user",user1);
                 model.addAttribute("msg1", "修改成功");
                return "forward:/user/alter";
            } else {
                model.addAttribute("msg2", "修改失败,请重新注册");
                return "forward:/user/alter";
            }
        }catch (Exception e){
            model.addAttribute("msg2", "修改失败,请重新注册");
            return "forward:/user/alter";
        }
    }
    @RequestMapping("/alterPassword")
    public  String userAlterPassword(Model model){
        return "alterPassword";
    }
    @RequestMapping("/alterImage")
    public  String userAlterImage(Model model){
        return "alterImage";
    }

    @RequestMapping("/checkingPassword")
    @ResponseBody
    public Resultvo checkingPassword( HttpServletRequest request,String password) {
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        String str = Md5Utils.md5(password, user.getUser_salt());
        Resultvo resultvo = new Resultvo();
        if (!str.equals(user.getPassword())){
            resultvo.setMsg( "密码不正确！请重新输入");
        }
        return resultvo;
    }

    @RequestMapping("/saveAlterPassword")
    @Transactional
    public String saveAlterPassword(HttpServletRequest request,String password,Model model) {
        try {
            HttpSession session = request.getSession();
            Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
            UserEntity user1 =(UserEntity) usermap.get("user");
            String str = Md5Utils.md5(password, user1.getUser_salt());
            UserEntity user = new UserEntity();
            user.setPassword(str);
            user.setUser_id(user1.getUser_id());
            int number = userService.saveuser(user);
            if (number == 1 ) {
                Map<String, Object> user2 = userService.authcUser(user1.getAccount_name());
                session.setAttribute("user",user2);
                model.addAttribute("msg1", "修改成功");
                return "forward:/user/alterPassword";
            } else {
                model.addAttribute("msg2", "修改失败,请重新修改1");
                return "forward:/user/alterPassword";
            }
        }catch (Exception e){
            model.addAttribute("msg2", "修改失败,请重新修改");
            return "forward:/user/alterPassword";
        }
    }
}
