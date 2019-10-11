package com.yb.managemodule.service.impl;

import com.yb.base.mapper.ProjectTrainMapper;
import com.yb.base.mapper.SubjectMapper;
import com.yb.base.pojo.ProjectTrainEntity;
import com.yb.base.pojo.SubjectEntity;
import com.yb.base.vo.ProjectTrainVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IProjectTrainService;
import org.apache.log4j.Logger;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2019/8/12.
 */
@Service
public class ProjectTrainService implements IProjectTrainService {

    private final static Logger LOGGER = Logger.getLogger(ProjectTrainService.class);


    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【ProjectTrainVo】不能为空";
    public static final String MESSAGE_LOGINIDREPEAT = "该项目名已存在";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";
    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";

    @Autowired
    private ProjectTrainMapper projectTrainMapper;
    @Autowired
    private SubjectMapper subjectMapper;


    @Override
    public List<ProjectTrainVo> queryPageProjectTrainInfo() {

        List<ProjectTrainVo> projectTrainVos = new ArrayList<>();

        List<ProjectTrainEntity> projectTrainList = projectTrainMapper.queryPageProjectTrainInfo();
        for (ProjectTrainEntity projectTrainEntity : projectTrainList) {
            ProjectTrainVo projectTrainVo = new ProjectTrainVo();
            BeanUtils.copyProperties(projectTrainEntity, projectTrainVo);

            projectTrainVos.add(projectTrainVo);
        }

        return projectTrainVos;
    }

    /**
     * 创建实训项目信息
     * @param projectTrainVo
     * @return
     */
    @Override
    public Result createProjectTrainInfo(ProjectTrainVo projectTrainVo) {
        Result result = new Result();
        //验证参数
        if (null == projectTrainVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }
        //实训项目唯一验证
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("pro_name", projectTrainVo.getPro_name());

            List<ProjectTrainEntity> projectTrainList = projectTrainMapper.selectByMap(params);
            if (projectTrainList.size() != 0) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_LOGINIDREPEAT);
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_LOGINIDREPEAT);
            return result;
        }
        //构建用户模型
        ProjectTrainEntity projectTrainEntity = new ProjectTrainEntity();

        //复制属性赋值给ProjectTrainEntity对象
        BeanUtils.copyProperties(projectTrainVo, projectTrainEntity);
        Date date = new Date();
        try {
            projectTrainEntity.setUpdate_time(date);

            projectTrainEntity.setCreate_time(date);


            //创建实训项目表信息
            projectTrainMapper.insert(projectTrainEntity);


            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    @Override
    public Result updateProjectTrainInfoById(int proId, ProjectTrainVo projectTrainVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(proId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        ProjectTrainEntity projectTrainEntity = projectTrainMapper.selectById(proId);

        projectTrainEntity.setPro_name(projectTrainVo.getPro_name());
        projectTrainEntity.setRemark(projectTrainVo.getRemark());

        SubjectEntity subjectEntity = subjectMapper.selectSubjectOne(projectTrainVo.getSub_name());
        projectTrainEntity.setSub_id(subjectEntity.getSub_id());
        projectTrainEntity.setColunm1(projectTrainVo.getColunm1());
        projectTrainEntity.setColunm2(projectTrainVo.getColunm2());
        projectTrainEntity.setUpdate_time(new Date());


        try{
            projectTrainMapper.updateById(projectTrainEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    @Override
    public Result deleteProjectTrainInfos(int[] proIds) {
        Result result = new Result();
        // 验证参数
        if (proIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int proId : proIds) {
                //删除项目表信息
                projectTrainMapper.deleteById(proId);

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }


}
