package com.yb.managemodule.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.vo.*;
import com.yb.managemodule.service.ITitleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 题库管理控制类
 * Created by Administrator on 2019/8/22.
 */

@RestController
@RequestMapping(value = "/manage/title")
@Log4j
public class TitleController {

    @Autowired
    private ITitleService titleService;

    /**
     * 分页查询题型列表
     * @param page
     * @return
     */
    @GetMapping("/queryPageTitleTypes")
    @ResponseBody
    public Object queryPageTitleTypes(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){
        List<TitleTypeVo> titleTypeVos = titleService.queryPageTitleTypeInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (titleTypeVos.size()<a){
            a=titleTypeVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(titleTypeVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(titleTypeVos.size());
        return page;
    }

    /**
     * 查询题型列表
     * @return
     */
    @GetMapping("/queryTitleTypeList")
    @ResponseBody
    public List<TitleTypeVo> queryTitleTypeList(){


        List<TitleTypeVo> titleTypeVos = titleService.queryPageTitleTypeInfo();

        return titleTypeVos;
    }

    /**
     * 创建题型
     * @param titleTypeVo
     * @return
     */
    @RequestMapping(value = "/createTitleTypeInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createTitleTypeInfo(TitleTypeVo titleTypeVo){

        Result result = titleService.createTitleTypeInfo(titleTypeVo);

        return result;
    }

    /**
     * 根据主键修改题型信息
     * @param titleTypeId
     * @param titleTypeVo
     * @return
     */
    @RequestMapping(value = "/updateTitleTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTitleTypeInfo(@RequestParam("title_type_id")int titleTypeId, TitleTypeVo titleTypeVo){

        Result result =titleService.updateTitleTypeInfo(titleTypeId,titleTypeVo);

        return result;
    }

    /**
     * 删除题型信息，支持批量删除
     * @param titleTypeIds
     * @return
     */
    @RequestMapping(value = "/deleteTitleTypeInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTitleTypeInfos(@RequestParam("titleTypeIds[]")int[] titleTypeIds){

        Result result =titleService.deleteTitleTypeInfo(titleTypeIds);

        return result;
    }





    /**
     * 分页查询题目列表
     * @param page
     * @return
     */
    @GetMapping("/queryPageTitles")
    @ResponseBody
    public Object queryPageTitles(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){

        List<TitleVo> titleVos = titleService.queryPageTitleInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (titleVos.size()<a){
            a=titleVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(titleVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(titleVos.size());
        return page;
    }

    @GetMapping("/queryPageTestTitleInfo")
    @ResponseBody
    public Object queryPageTestTitleInfo(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){
        List<TitleVo> titleVos = titleService.queryPageTestTitleInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (titleVos.size()<a){
            a=titleVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(titleVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(titleVos.size());
        return page;
    }

    /**
     * 根据条件分页查询题目列表
     * @return
     */
    @GetMapping("/queryTitleListByParams")
    @ResponseBody
    public Object queryTitleListByParams(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "title_content") String titleContent,@RequestParam(value = "title_type_name") String titleTypeName){
        List<TitleVo> titleVos = titleService.queryPageTitleByParams(titleContent,titleTypeName);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (titleVos.size()<a){
            a=titleVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(titleVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(titleVos.size());
        return page;
    }

    /**
     * 创建题目
     * @param titleVo
     * @return
     */
    @RequestMapping(value = "/createTitleInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createTitleInfo(TitleVo titleVo){

        Result result = titleService.createTitleInfo(titleVo);

        return result;
    }

    /**
     * 根据主键修改题目信息
     * @param titleId
     * @param titleVo
     * @return
     */
    @RequestMapping(value = "/updateTitleInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTitleInfo(@RequestParam("title_id")int titleId, TitleVo titleVo){

        Result result =titleService.updateTitleInfo(titleId,titleVo);

        return result;
    }

    /**
     * 删除题目信息，支持批量删除
     * @param titleIds
     * @return
     */
    @RequestMapping(value = "/deleteTitleInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTitleInfos(@RequestParam("titleIds[]")int[] titleIds){

        Result result =titleService.deleteTitleInfo(titleIds);

        return result;
    }




    @GetMapping("/queryPageAnswerInfo")
    @ResponseBody
    public Object queryPageAnswerInfo(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){
        List<AnswerVo> answerVos = titleService.queryPageAnswerInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (answerVos.size()<a){
            a=answerVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(answerVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(answerVos.size());
        return page;
    }


    /**
     * 创建题型
     * @param
     * @return
     */
    @RequestMapping(value = "/createAnswerInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createAnswerInfo(AnswerListVo answerListVo){

        Result result = titleService.createAnswerInfo(answerListVo);

        return result;
    }

    /**
     * 根据主键修改题型信息
     * @param answerId
     * @param answerVo
     * @return
     */
    @RequestMapping(value = "/updateAnswerInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateAnswerInfo(@RequestParam("answer_id")int answerId, AnswerVo answerVo){

        Result result =titleService.updateAnswerInfo(answerId,answerVo);

        return result;
    }

    /**
     * 删除题型信息，支持批量删除
     * @param answerIds
     * @return
     */
    @RequestMapping(value = "/deleteAnswerInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAnswerInfo(@RequestParam("answerIds[]")int[] answerIds){

        Result result =titleService.deleteAnswerInfo(answerIds);

        return result;
    }















}
