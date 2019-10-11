package com.yb.managemodule.controller;

import com.yb.base.vo.RoleVo;
import com.yb.managemodule.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/8/9.
 */


@RestController
@RequestMapping(value = "/manage/role")
public class RoleManageController {

    @Autowired
    private IRoleService roleService;

    /**
     * 分页查询用户信息
     * @return
     */
    @GetMapping("/queryPageRole")
    @ResponseBody
    public List<RoleVo> queryPageRoleInfo(Model model) {
        //PageHelper.setPagination(page);
        List<RoleVo> roles = roleService.queryPageRoleInfo();
       // page.setRecords(roles);
        //page.setTotal(roles.size());//获取总数并释放资源 也可以 PageHelper.getTotal()
        model.addAttribute("user",1);
        return roles;
    }

}
