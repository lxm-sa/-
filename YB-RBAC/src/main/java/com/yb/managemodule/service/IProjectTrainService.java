package com.yb.managemodule.service;

import com.yb.base.vo.ProjectTrainVo;
import com.yb.base.vo.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */
public interface IProjectTrainService {

    /**
     * 分页查询，实训项目列表
     * @return
     */
    List<ProjectTrainVo> queryPageProjectTrainInfo();

    /**
     * 创建实训项目信息
     * @param projectTrainVo
     * @return
     */
    Result createProjectTrainInfo(ProjectTrainVo projectTrainVo);

    /**
     * 修改实训项目信息
     * @param proId
     * @param projectTrainVo
     * @return
     */
    Result updateProjectTrainInfoById(int proId, ProjectTrainVo projectTrainVo);

    /**
     * 删除实训项目信息，支持批量删除
     * @param proIds
     * @return
     */
    Result deleteProjectTrainInfos(int[] proIds);

}
