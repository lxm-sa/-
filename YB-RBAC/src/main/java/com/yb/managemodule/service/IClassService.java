package com.yb.managemodule.service;

import com.yb.base.vo.ClassVo;
import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;

import java.util.List;

/**
 * Created by Administrator on 2019/9/9 0009.
 */
public interface IClassService {

    /**
     * 分页查询班级列表
     * @return
     */
    List<ClassVo> queryPageClassInfos(int teachId);

    /**
     * 创建班级信息
     * @param classVo
     * @return
     */
    Result createClassInfo(ClassVo classVo);

    /**
     * 修改班级信息
     * @param class_id
     * @param classVo
     * @return
     */
    Result updateClassInfo(int class_id, ClassVo classVo);

    /**
     * 删除班级信息
     * @param classIds
     * @return
     */
    Result deleteClassInfo(int[] classIds);

    /**
     * 修改班级状态
     * @param classId
     * @param status
     * @return
     */
    Result updateClassStatusById(int classId, int status);

    /**
     * 根据班级编号查询班级学员
     * @param classId
     * @return
     */
    List<UserVo> queryUsersByClassId(int classId);

    /**
     * 删除班级学员关联表信息
     * @param classId
     * @param stuIds
     * @return
     */
    Result deleteClassStuInfo(int classId, int[] stuIds);

    /**
     * 创建班级学员信息
     * @param classId
     * @param stuIds
     * @return
     */
    Result createClassStuInfos(int classId, int[] stuIds);
}
