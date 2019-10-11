package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.vo.ProjectTrainVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IProjectTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */

@RestController
@RequestMapping(value = "/manage/projectTrain")
public class ProjectTrainController {
    @Autowired
    private IProjectTrainService projectTrainService;


    /**
     * 分页查询实训项目列表
     * @param page
     * @return
     */
    @GetMapping("/queryPageProjectTrain")
    @ResponseBody
    public Object queryPageProjectTrain(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){

        List<ProjectTrainVo> projectTrainVos = projectTrainService.queryPageProjectTrainInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (projectTrainVos.size()<a){
            a=projectTrainVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(projectTrainVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(projectTrainVos.size());
        return page;
    }

    @GetMapping("/queryProjectTrainList")
    @ResponseBody
    public List<ProjectTrainVo> queryProjectTrainList(){

        List<ProjectTrainVo> projectTrainVos = projectTrainService.queryPageProjectTrainInfo();

        return projectTrainVos;
    }


    /**
     * 创建实训项目信息
     * @param projectTrainVo
     * @return
     */
    @RequestMapping(value = "/createProjectTrainInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createProjectTrainInfo(ProjectTrainVo projectTrainVo){

        Result result = projectTrainService.createProjectTrainInfo(projectTrainVo);

        return result;
    }

    @RequestMapping(value = "/updateProjectTrainInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProjectTrainInfoById(@RequestParam("pro_id")int proId, ProjectTrainVo projectTrainVo){

        Result result = projectTrainService.updateProjectTrainInfoById(proId, projectTrainVo);

        return result;
    }

    @RequestMapping(value = "/deleteProjectTrainInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteProjectTrainInfos(@RequestParam("proIds[]")int[] proIds){

        Result result = projectTrainService.deleteProjectTrainInfos(proIds);

        return result;
    }
}
