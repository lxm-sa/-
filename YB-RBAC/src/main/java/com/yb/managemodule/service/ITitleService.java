package com.yb.managemodule.service;

import com.yb.base.vo.*;

import java.util.List;

/**
 * 题库管理服务接口类
 * Created by Administrator on 2019/8/22.
 */
public interface ITitleService {


    /**
     * 查询题型列表，支持分页查询
     * @return
     */
    List<TitleTypeVo> queryPageTitleTypeInfo();

    /**
     * 创建题型
     * @param titleTypeVo
     * @return
     */
    Result createTitleTypeInfo(TitleTypeVo titleTypeVo);

    /**
     * 修改题型信息
     * @param titleTypeId
     * @param titleTypeVo
     * @return
     */
    Result updateTitleTypeInfo(int titleTypeId, TitleTypeVo titleTypeVo);

    /**
     * 删除题型，支持批量删除
     * @param titleTypeIds
     * @return
     */
    Result deleteTitleTypeInfo(int[] titleTypeIds);


    /**********************************题目管理*******************************************/

    /**
     * 分页查询题目信息
     * @return
     */
    List<TitleVo> queryPageTitleInfo();


    /**
     * 查询考试题目
     * @return
     */
    List<TitleVo> queryPageTestTitleInfo();
    /**
     * 根据题型查询题目
     * @param titleContent
     * @param titleTypeName
     * @return
     */
    List<TitleVo> queryPageTitleByParams(String titleContent, String titleTypeName);

    /**
     * 创建题目
     * @param titleVo
     * @return
     */
    Result createTitleInfo(TitleVo titleVo);

    /**
     * 修改题目信息
     * @param titleId
     * @param titleVo
     * @return
     */
    Result updateTitleInfo(int titleId, TitleVo titleVo);

    /**
     * 删除题目，支持批量删除
     * @param titleIds
     * @return
     */
    Result deleteTitleInfo(int[] titleIds);


    /********************************************答案管理部分*********************************************/


    /**
     * 分页查询答案列表信息
     * @return
     */
    List<AnswerVo> queryPageAnswerInfo();


    /**
     * 创建答案信息
     * @param
     * @return
     */
    Result createAnswerInfo(AnswerListVo answerListVo);


    /**
     * 修改答案内容
     * @param answer_id
     * @param answerVo
     * @return
     */
    Result updateAnswerInfo(int answer_id, AnswerVo answerVo);


    /**
     * 删除答案信息，支持批量删除
     * @param answerIds
     * @return
     */
    Result deleteAnswerInfo(int[] answerIds);

}
