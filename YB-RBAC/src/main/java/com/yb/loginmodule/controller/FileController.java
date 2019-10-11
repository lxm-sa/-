package com.yb.loginmodule.controller;

import com.yb.base.pojo.UserEntity;
import com.yb.loginmodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by mayn on 2019/8/20.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private UserService userService;
    @RequestMapping("/upload") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Transactional
    public String uplaod(HttpServletRequest request,MultipartFile file, Model model) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
        try {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

            file.transferTo(dest);
        String filename = "/temp-rainy/" + fileName;
        HttpSession session = request.getSession();
        Map<String, Object> usermap = (Map<String, Object>) session.getAttribute("user");
            UserEntity user1 = (UserEntity) usermap.get("user");
            UserEntity user = new UserEntity();
        user.setUrl(filename);
        user.setUser_id(user1.getUser_id());
        int number = userService.saveuser(user);
        if (number == 1) {
            Map<String, Object> user2 = userService.authcUser(user1.getAccount_name());
            session.setAttribute("user", user2);
            model.addAttribute("msg1", "头像修改成功！");
            model.addAttribute("filename", filename);
        } else {
            model.addAttribute("msg2", "头像修改失败！");
        }

        return "alterImage";
    }catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg2", "头像修改失败！");
            return  "alterImage";
        }
    }

}
