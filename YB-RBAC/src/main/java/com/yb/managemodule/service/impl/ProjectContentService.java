package com.yb.managemodule.service.impl;


import com.yb.base.mapper.ContentPartsMapper;
import com.yb.base.mapper.PartsMapper;
import com.yb.base.mapper.ProjectContentMapper;
import com.yb.base.mapper.ProjectTrainMapper;
import com.yb.base.pojo.ContentPartsEntity;
import com.yb.base.pojo.PartsEntity;
import com.yb.base.pojo.ProjectContentEntity;
import com.yb.base.pojo.ProjectTrainEntity;
import com.yb.base.vo.PartsVo;
import com.yb.base.vo.ProjectContentVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IProjectContentService;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2019/8/14.
 */
@Service
public class ProjectContentService implements IProjectContentService {
    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【ProjectContentVo】不能为空";
    public static final String MESSAGE_NAMEREPEAT = "该名称已存在";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";

    @Autowired
    private ProjectContentMapper contentMapper;
    @Autowired
    private ContentPartsMapper contentPartsMapper;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private ProjectTrainMapper projectTrainMapper;


    @Override
    public List<ProjectContentVo> queryPageProContentInfo() {

        List<ProjectContentVo> contentVoList = new ArrayList<>() ;

        List<ProjectContentEntity> contentEntityList = contentMapper.selectProContetAll();


        for (ProjectContentEntity projectContentEntity : contentEntityList) {
            ProjectContentVo contentVo = new ProjectContentVo();
            BeanUtils.copyProperties(projectContentEntity, contentVo);
            contentVoList.add(contentVo);
        }

        return contentVoList;
    }

    @Override
    public List<PartsVo> queryPartInfosByParams(int contentId) {
        List<PartsVo> partsVoList = new ArrayList<>();
        List<ContentPartsEntity> contentPartsList = contentPartsMapper.selectContentPartsByParams(contentId);
        for (ContentPartsEntity contentPartsEntity : contentPartsList) {
            PartsVo partsVo = new PartsVo();
            PartsEntity partsEntity = partsMapper.selectById(contentPartsEntity.getPart_id());

            BeanUtils.copyProperties(partsEntity, partsVo);
            partsVoList.add(partsVo);
        }

        return partsVoList;
    }


    @Override
    public Result createProContentInfo(ProjectContentVo projectContentVo) {
        Result result = new Result();
        //验证参数
        if (null == projectContentVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }
        //实训项目唯一验证
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("content_name", projectContentVo.getContent_name());

            List<ProjectContentEntity> projectContentList = contentMapper.selectByMap(params);
            if (projectContentList.size() != 0) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_NAMEREPEAT);
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_NAMEREPEAT);
            return result;
        }
        //构建用户模型
        ProjectContentEntity projectContentEntity = new ProjectContentEntity();

        //复制属性赋值给ProjectTrainEntity对象
        BeanUtils.copyProperties(projectContentVo, projectContentEntity);
        Date date = new Date();
        try {
            projectContentEntity.setUpdate_time(date);

            projectContentEntity.setCreate_time(date);

            //创建实训项目表信息
            contentMapper.insert(projectContentEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }

    @Override
    public Result updateProContentInfo(int contentId, ProjectContentVo projectContentVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(contentId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        ProjectContentEntity projectContentEntity = contentMapper.selectById(contentId);
        projectContentEntity.setContent_name(projectContentVo.getContent_name());

        ProjectTrainEntity projectTrainEntity = projectTrainMapper.selectProOneByParam(projectContentVo.getPro_name());
        projectContentEntity.setPro_id(projectTrainEntity.getPro_id());

        projectContentEntity.setRemark(projectContentVo.getRemark());
        projectContentEntity.setUpdate_time(new Date());


        try{
            contentMapper.updateById(projectContentEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    @Override
    @Transactional
    public Result deleteProContentInfo(int[] contentIds) {
        Result result = new Result();
        // 验证参数
        if (contentIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int contentId : contentIds) {
                Map<String, Object> params = new HashMap<>();
                params.put("content_id", contentId);
                //删除内容部件表关联信息
                contentPartsMapper.deleteByMap(params);

                //删除项目内容表信息
                contentMapper.deleteById(contentId);

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }

    @Override
    public Result deleteContentParts(int contentId, int[] partIds) {
        Result result = new Result();
        // 验证参数
        if (partIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int partId : partIds) {
                Map<String, Object> params = new HashMap<>();
                params.put("content_id", contentId);
                params.put("part_id", partId);
                //删除内容部件表关联信息
                contentPartsMapper.deleteByMap(params);


            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }

    @Override
    public Result createContentPartInfo(int contentId, int[] partIds) {
        Result result = new Result();
        // 验证参数
        if (partIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int partId : partIds) {

                ContentPartsEntity contentPartsEntities = contentPartsMapper.selectContentPartsInfo(contentId,partId);
                if(null==contentPartsEntities){ //当为空不存在时，则新增数据

                    ContentPartsEntity contentPartsEntity = new ContentPartsEntity();
                    contentPartsEntity.setContent_id(contentId);
                    contentPartsEntity.setPart_id(partId);
                    contentPartsEntity.setCreate_time(new Date());
                    contentPartsEntity.setUpdate_time(new Date());
                    //创建内容部件表信息
                    contentPartsMapper.insert(contentPartsEntity);
                }else{
                    //已存在，则忽略
                }
            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }
}
