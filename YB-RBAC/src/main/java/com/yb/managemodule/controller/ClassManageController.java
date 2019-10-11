package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.pojo.UserEntity;
import com.yb.base.vo.ClassVo;
import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;
import com.yb.managemodule.service.IClassService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/9/9 0009.
 */


@RestController
@RequestMapping(value = "/manage/class")
@Log4j
public class ClassManageController {

    private final static Logger LOGGER = Logger.getLogger(ClassManageController.class);
    @Autowired
    private IClassService classService;


    @GetMapping("/queryPageClass")
    @ResponseBody
    public Object queryPageClass(Page page,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();

        List<ClassVo> classVos = classService.queryPageClassInfos(teachId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (classVos.size()<a){
            a=classVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(classVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(classVos.size());
        return page;
    }

    @RequestMapping(value = "/createClassInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createClassInfo(ClassVo classVo){

        Result result =classService.createClassInfo(classVo);

        return result;
    }

    @RequestMapping(value = "/updateClassInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateClassInfo(@RequestParam("class_id")int class_id, ClassVo classVo){

        Result result =classService.updateClassInfo(class_id,classVo);

        return result;
    }
    @RequestMapping(value = "/deleteClassInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteClassInfos(@RequestParam("classIds[]")int[] classIds){

        Result result =classService.deleteClassInfo(classIds);

        return result;
    }

    @RequestMapping(value = "/updateClassStatusById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateClassStatusById(@RequestParam("class_id")int classId,@RequestParam("status") int status) {

        Result result = classService.updateClassStatusById(classId,status);

        return result;
    }

    @GetMapping("/queryUsersByClassId")
    @ResponseBody
    public Object queryUsersByClassId(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "class_id", required =
            true)int classId){

        List<UserVo> userVos = classService.queryUsersByClassId(classId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (userVos.size()<a){
            a=userVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(userVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(userVos.size());
        return page;
    }

    @RequestMapping(value = "/deleteClassStuInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteClassStuInfos(@RequestParam("classId")int classId,@RequestParam("stuIds[]") int[] stuIds){

        Result result =classService.deleteClassStuInfo(classId,stuIds);

        return result;
    }

    @RequestMapping(value = "/createClassStuInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result createClassStuInfos(@RequestParam("classId")int testId,@RequestParam("stuIds[]") int[] stuIds){

        Result result =classService.createClassStuInfos(testId,stuIds);

        return result;
    }
}
