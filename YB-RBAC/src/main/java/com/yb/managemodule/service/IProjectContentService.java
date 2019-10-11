package com.yb.managemodule.service;

import com.yb.base.vo.PartsVo;
import com.yb.base.vo.ProjectContentVo;
import com.yb.base.vo.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/8/14.
 */
public interface IProjectContentService {

    /**
     * 项目内容分页列表
     * @return
     */
    List<ProjectContentVo> queryPageProContentInfo();

    /**
     * 根据项目内容Id查询其下部件
     * @param contentId
     * @return
     */
    List<PartsVo> queryPartInfosByParams(int contentId);
    /**
     * 创建项目内容信息
     * @param projectContentVo
     * @return
     */
    Result createProContentInfo(ProjectContentVo projectContentVo);

    /**
     * 修改项目内容信息
     * @param contentId
     * @param projectContentVo
     * @return
     */
    Result updateProContentInfo(int contentId, ProjectContentVo projectContentVo);

    /**
     * 删除项目内容信息，支持批量删除
     * @param contentIds
     * @return
     */
    Result deleteProContentInfo(int[] contentIds);

    /**
     * 删除内容关联的部件信息，支持批量删除
     * @param contentId
     * @param partIds
     * @return
     */
    Result deleteContentParts(int contentId, int[] partIds);

    /**
     * 创建项目内容部件表信息
     * @param contentId
     * @param partIds
     * @return
     */
    Result createContentPartInfo(int contentId, int[] partIds);
}
