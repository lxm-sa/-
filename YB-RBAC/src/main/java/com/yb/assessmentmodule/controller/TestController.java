package com.yb.assessmentmodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.yb.assessmentmodule.service.ITestService;
import com.yb.base.pojo.UserEntity;
import com.yb.base.vo.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/8/26.
 */

@RestController
@RequestMapping(value = "/manage/test")
@Log4j
public class TestController {

    @Autowired
    private ITestService testService;


    @GetMapping("/queryPageTests")
    @ResponseBody
    public Object queryPageTests(Page page,Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,HttpServletRequest request){
        PageHelper.setPagination(page);
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();
        List<TestVo> testVos = testService.queryPageTestInfo(teachId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (testVos.size()<a){
            a=testVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(testVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(testVos.size());
        return page;
    }

    @GetMapping("/queryUsersByParams")
    @ResponseBody
    public Object queryUsersByParams(@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "test_id", required =
            true)int testId){

        PageHelper.setPagination(page);
        List<UserVo> userVos = testService.queryUsersByParams(testId);
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



    /**
     * 创建试卷
     * @param testVo
     * @return
     */
    @RequestMapping(value = "/createTestInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createTestInfo(TestVo testVo,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();
        Result result = testService.createTestInfo(testVo,teachId);

        return result;
    }

    /**
     * 根据主键修改题型信息
     * @param testId
     * @param testVo
     * @return
     */
    @RequestMapping(value = "/updateTestInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTestInfo(@RequestParam("test_id")int testId, TestVo testVo,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();
        Result result =testService.updateTestInfo(testId,testVo,teachId);

        return result;
    }
    /**
     * 删除题型信息，支持批量删除
     * @param testIds
     * @return
     */
    @RequestMapping(value = "/deleteTestInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTestInfos(@RequestParam("testIds[]")int[] testIds){

        Result result =testService.deleteTestInfos(testIds);

        return result;
    }

    /**
     * 删除学生试卷关联表信息
     * @param testId
     * @param stuIds
     * @return
     */
    @RequestMapping(value = "/deleteStuTestInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStuTestInfo(@RequestParam("testId")int testId,@RequestParam("stuIds[]") int[] stuIds){

        Result result =testService.deleteStuTestInfo(testId,stuIds);

        return result;
    }

    /**
     * 创建学员试卷关联表信息
     * @param testId
     * @param stuIds
     * @return
     */
    @RequestMapping(value = "/createStuTestInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result createStuTestInfos(@RequestParam("testId")int testId,@RequestParam("stuIds[]") int[] stuIds){

        Result result =testService.createStuTestInfos(testId,stuIds);

        return result;
    }


    /**
     * 根据试卷编号查询试卷题目
     * @param testId
     * @return
     */
    @GetMapping("/queryTitleByParams")
    @ResponseBody
    public Object queryTitleByParams(@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "test_id", required =
            true)int testId){

        PageHelper.setPagination(page);
        List<TestTitleVo> testTitleVos = testService.queryTitleByTestId(testId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (testTitleVos.size()<a){
            a=testTitleVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(testTitleVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(testTitleVos.size());
        return page;
    }

    /**
     * 根据规则查询试卷题目
     * @param testId
     * @param testRule
     * @return
     */
    @GetMapping("/queryTitleListById")
    @ResponseBody
    public List<TitleAnswerVo> queryTitleListById(@RequestParam("testId")int testId,@RequestParam("testRule")int testRule){

        List<TitleAnswerVo> titleAnswerVos  = testService.queryTitleByParams(testId,testRule);

        return titleAnswerVos;
    }


    /**
     * 删除试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    @RequestMapping(value = "/deleteTestTitleInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTestTitleInfos(@RequestParam("testId")int testId,@RequestParam("titleIds[]") int[] titleIds){

        Result result =testService.deleteTestTitleInfos(testId,titleIds);

        return result;
    }


    /**
     * 创建试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    @RequestMapping(value = "/createTestTitleInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result createTestTitleInfos(@RequestParam("testId")int testId,@RequestParam("titleIds[]") int[] titleIds){

        Result result =testService.createTestTitleInfos(testId,titleIds);

        return result;
    }

    /**
     * 查询学生考试信息
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/queryPageStuTestInfos")
    @ResponseBody
    public Object queryPageStuTestInfos(Page page,HttpServletRequest request,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int stuId = user.getUser_id();
        PageHelper.setPagination(page);
        List<StuTestVo> stuTestVos = testService.queryPageStuTestInfos(stuId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (stuTestVos.size()<a){
            a=stuTestVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(stuTestVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(stuTestVos.size());
        return page;
    }

    /**
     * 创建学生答案表
     * @param testId
     * @param titleId
     * @param stuAnswerId
     * @param count
     * @return
     */
    @RequestMapping(value = "/createStuAnswerInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result createStuAnswerInfo(HttpServletRequest request,@RequestParam("testId")int testId, @RequestParam("titleId")int titleId, @RequestParam("stuAnswerId")String stuAnswerId,
                                      @RequestParam("count")int count){

        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int stuId = user.getUser_id();
        Result result =testService.createStuAnswerInfo(stuId,testId,titleId,stuAnswerId,count);

        return result;
    }

    /**
     * 生成学生成绩单
     * @param request
     * @param testId
     * @param testTime
     * @param scoreNumber
     * @return
     */
    @RequestMapping(value = "/createStuScoreInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result createStuScoreInfo(HttpServletRequest request,@RequestParam("testId")int testId, @RequestParam("testTime")String testTime,
                                     @RequestParam("scoreNumber")int scoreNumber,@RequestParam("array[]")int[]  array){

        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int userId = user.getUser_id();
        Result result =testService.createStuScoreInfo(userId,testId,testTime,scoreNumber,array);

        return result;
    }

    /**
     * 查询学生每道题的答案
     * @param request
     * @param testId
     * @param titleId
     * @param count
     * @return
     */
    @GetMapping("/queryStuAnswerInfo")
    @ResponseBody
    public StuAnswerVo queryStuAnswerInfo(HttpServletRequest request,@RequestParam("testId")int testId, @RequestParam("titleId")int titleId,
                                      @RequestParam("count")int count){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int stuId = user.getUser_id();
        StuAnswerVo stuAnswerVo = testService.queryStuAnswerInfo(stuId, testId, titleId, count);

        return stuAnswerVo;
    }

    /**
     * 查询学员已完成的考试成绩列表
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/queryStuTestScores")
    @ResponseBody
    public Object queryStuTestScores(Page page,HttpServletRequest request,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int stuId = user.getUser_id();
        PageHelper.setPagination(page);
        List<ScoreVo> scoreVos = testService.queryCompleteTestScores(stuId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (scoreVos.size()<a){
            a=scoreVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(scoreVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(scoreVos.size());
        return page;
    }

    @GetMapping("/queryStuTsetAnswerInfos")
    @ResponseBody
    public List<StuAnswerVo> queryStuTsetAnswerInfos(HttpServletRequest request,@RequestParam("testId")int testId,
                                          @RequestParam("count")int count){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int userId = user.getUser_id();
        List<StuAnswerVo> stuAnswerVos = testService.queryStuTsetAnswerInfos(userId, testId, count);

        return stuAnswerVos;
    }

    @GetMapping("/queryPageExpireTestInfos")
    @ResponseBody
    public Object queryPageExpireTestInfos(Page page,HttpServletRequest request,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber){
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int stuId = user.getUser_id();
        PageHelper.setPagination(page);
        List<StuTestVo> stuTestVos = testService.queryPageExpireTestInfos(stuId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (stuTestVos.size()<a){
            a=stuTestVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(stuTestVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(stuTestVos.size());
        return page;
    }

}
