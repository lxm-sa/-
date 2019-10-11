package com.yb.assessmentmodule.service;

import com.yb.base.vo.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2019/8/26.
 */
public interface ITestService {


    /**
     * 试卷列表查询，适用分页
     * @return
     */
    List<TestVo> queryPageTestInfo(int teachId);

    /**
     * 根据试卷Id查询学员列表
     * @param testId
     * @return
     */
    List<UserVo> queryUsersByParams(int testId);

    /**
     * 创建试卷信息
     * @param testVo
     * @return
     */
    Result createTestInfo(TestVo testVo,int teachId);

    /**
     * 修改试卷信息
     * @param testId
     * @param testVo
     * @return
     */
    Result updateTestInfo(int testId, TestVo testVo,int teachId);

    /**
     * 删除试卷信息，支持批量删除
     * @param testIds
     * @return
     */
    Result deleteTestInfos(int[] testIds);


    /**
     * 删除学生试卷关联表信息
     * @param testId
     * @param stuIds
     * @return
     */
    Result deleteStuTestInfo(int testId, int[] stuIds);

    /**
     * 创建学员试卷关联表信息
     * @param testId
     * @param stuIds
     * @return
     */
    Result createStuTestInfos(int testId, int[] stuIds);


    /**
     * 根据试卷编号查询试卷题目
     * @param testId
     * @return
     */
    List<TestTitleVo> queryTitleByTestId(int testId);

    /**
     * 根据出题规则查询试卷题目
     * @param testId
     * @param testRule
     * @return
     */
    List<TitleAnswerVo> queryTitleByParams(int testId, int testRule);


    /**
     * 删除试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    Result deleteTestTitleInfos(int testId, int[] titleIds);

    /**
     * 创建试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    Result createTestTitleInfos(int testId, int[] titleIds);

    /**
     * 查询当前学员的试卷信息列表
     * @return
     */
    List<StuTestVo> queryPageStuTestInfos(int stuId);

    /**
     * 修改学员考试次数
     * @param stuId
     * @param testId
     * @param testCount
     * @return
     */
    Result updateStuTestInfo(int stuId, int testId, int testCount);

    /**
     * 创建学生考试答案
     * @param testId
     * @param titleId
     * @param stuAnswerId
     * @return
     */
    Result createStuAnswerInfo(int stuId, int testId, int titleId, String stuAnswerId, int count);

    /**
     * 生成学生成绩
     * @param testId
     * @param testTime
     * @return
     */
    Result createStuScoreInfo(int userId, int testId, String testTime, int scoreNumber,int[]  array);

    /**
     * 查找学生考试考题答案
     * @param stuId
     * @param testId
     * @param titleId
     * @param count
     * @return
     */
    StuAnswerVo queryStuAnswerInfo(int stuId, int testId, int titleId, int count);


    /**
     * 查询学员已完成的试卷成绩
     * @param stuId
     * @return
     */
    List<ScoreVo> queryCompleteTestScores(int stuId);

    /**
     * 查询学员考试完成后的答题详情
     * @param userId
     * @param testId
     * @param count
     * @return
     */
    List<StuAnswerVo> queryStuTsetAnswerInfos(int userId, int testId, int count);

    /**
     * 分页查询未考已过期的试卷
     * @param stuId
     * @return
     */
    List<StuTestVo> queryPageExpireTestInfos(int stuId);

}
