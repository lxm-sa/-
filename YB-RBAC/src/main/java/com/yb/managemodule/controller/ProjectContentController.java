package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.vo.PartsVo;
import com.yb.base.vo.ProjectContentVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/14.
 */

@RestController
@RequestMapping(value = "/manage/content")
public class ProjectContentController {



    @Autowired
    private IProjectContentService contentService;


    /**
     * 分页查询项目内容信息
     * @return
     */
    @GetMapping("/queryPageProContentInfo")
    @ResponseBody
    public Object queryPageProContentInfo(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page) {
        List<ProjectContentVo> contentVoList = contentService.queryPageProContentInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (contentVoList.size()<a){
            a=contentVoList.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(contentVoList.get(i));
        }
        page.setRecords(list);
        page.setTotal(contentVoList.size());
        return page;
    }

    @GetMapping("/queryPartInfosByParams")
    @ResponseBody
    public Object queryPartInfosByParams(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "content_id", required =
            true)int contentId) {
        List<PartsVo> partsVos = contentService.queryPartInfosByParams(contentId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (partsVos.size()<a){
            a=partsVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(partsVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(partsVos.size());
        return page;
    }
    @RequestMapping(value = "/createProContentInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createProContentInfo(ProjectContentVo projectContentVo){

        Result result = contentService.createProContentInfo(projectContentVo);

        return result;
    }

    @RequestMapping(value = "/updateProContentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProContentInfo(@RequestParam("content_id")int contentId, ProjectContentVo projectContentVo){

        Result result = contentService.updateProContentInfo(contentId,projectContentVo);

        return result;
    }

    @RequestMapping(value = "/deleteProContentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteProContentInfo(@RequestParam("contentIds[]")int[] contentIds){

        Result result =contentService.deleteProContentInfo(contentIds);

        return result;
    }
    @RequestMapping(value = "/deleteContentParts", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteContentParts(@RequestParam("contentId")int contentId,@RequestParam("partIds[]") int[] partIds){

        Result result =contentService.deleteContentParts(contentId,partIds);

        return result;
    }

    @RequestMapping(value = "/createContentPartInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result createContentPartInfo(@RequestParam("contentId")int contentId,@RequestParam("partIds[]") int[] partIds){

        Result result =contentService.createContentPartInfo(contentId,partIds);

        return result;
    }


}
