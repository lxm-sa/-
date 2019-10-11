package com.yb.managemodule.service;

import com.yb.base.vo.Result;
import com.yb.base.vo.SubjectVo;

import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */
public interface ISubjectService {


    /**
     * 分页查询，用户列表
     * @return
     */
    List<SubjectVo> queryPageSubjectInfo();

    /**
     * 新增科目信息
     * @param subjectVo
     * @return
     */
    Result createSubjectInfo(SubjectVo subjectVo);

    /**
     * 删除科目信息
     * @param subIds
     * @return
     */
    Result deleteSubjects(Integer[] subIds);

    /**
     * 修改科目信息
     * @param subId
     * @param subjectVo
     * @return
     */
    Result updateSubjectById(int subId, SubjectVo subjectVo);

}
