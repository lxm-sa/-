package com.yb.managemodule.service.impl;

import com.yb.base.mapper.SubjectMapper;
import com.yb.base.pojo.SubjectEntity;
import com.yb.base.vo.Result;
import com.yb.base.vo.SubjectVo;

import com.yb.managemodule.service.ISubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.assertj.core.util.Strings;
import java.util.*;

/**
 * Created by Administrator on 2019/8/12.
 */
@Service
public class SubjectService implements ISubjectService {

    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【SubjectVo】不能为空";
    public static final String MESSAGE_LOGINIDREPEAT = "该登录账户名已存在";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<SubjectVo> queryPageSubjectInfo() {
        List<SubjectVo> subjectVos = new ArrayList<>();

        List<SubjectEntity> subjectList = subjectMapper.selectSubjectAll();

        for (SubjectEntity subjectEntity : subjectList) {

            SubjectVo subjectVo = new SubjectVo();

            BeanUtils.copyProperties(subjectEntity, subjectVo);

            subjectVos.add(subjectVo);
        }

        return subjectVos;
    }

    @Override
    public Result createSubjectInfo(SubjectVo subjectVo) {
        Result result = new Result();
        //验证参数
        if (null == subjectVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }
        //科目名称唯一验证
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("sub_name", subjectVo.getSub_name());

            List<SubjectEntity> subjectList = subjectMapper.selectByMap(params);
            if (subjectList.size() != 0) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_LOGINIDREPEAT);
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_LOGINIDREPEAT);
            return result;
        }
        //构建科目模型
        SubjectEntity subject = new SubjectEntity();

        //复制属性赋值给User对象
        BeanUtils.copyProperties(subjectVo, subject);
        Date date = new Date();
        try {
            subject.setUpdate_time(date);

            subject.setCreate_time(date);

            //创建科目表信息
            subjectMapper.insert(subject);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    /**
     * 批量删除科目信息
     * @param subIds
     * @return
     */
    @Override
    public Result deleteSubjects(Integer[] subIds) {
        Result result = new Result();
        // 验证参数
        if (subIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int subId : subIds) {
                    //删除科目表信息
                SubjectEntity subjectEntity = subjectMapper.selectById(subId);
                subjectEntity.setIsdelete(1);
                subjectMapper.updateById(subjectEntity);

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
    public Result updateSubjectById(int subId, SubjectVo subjectVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(subId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        SubjectEntity subjectEntity = subjectMapper.selectById(subId);
        subjectEntity.setSub_name(subjectVo.getSub_name());
        subjectEntity.setRemark(subjectVo.getRemark());
        subjectEntity.setUpdate_time(new Date());


        try{
            subjectMapper.updateById(subjectEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }
}
