package com.yb.managemodule.service.impl;

import com.yb.base.mapper.ClassMapper;
import com.yb.base.mapper.ClassStudentMapper;
import com.yb.base.mapper.UserMapper;
import com.yb.base.mapper.UserRoleRefMapper;
import com.yb.base.pojo.ClassEntity;
import com.yb.base.pojo.ClassStudentEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.base.pojo.UserRoleRefEntity;
import com.yb.base.vo.ClassVo;
import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;
import com.yb.managemodule.service.IClassService;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2019/9/9 0009.
 */
@Service
public class ClassService implements IClassService {

    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【CategoryVo】不能为空";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";
    public static final String MESSAGE_PARAM_STATUS_START = "正在授课";
    public static final String MESSAGE_PARAM_STATUS_STOP = "已经结业";

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private ClassStudentMapper classStudentMapper;
    @Autowired
    private UserRoleRefMapper userRoleRefMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<ClassVo> queryPageClassInfos(int teachId) {
        List<ClassVo> classVos = new ArrayList<>();
        UserRoleRefEntity userRoleRefEntity = userRoleRefMapper.selectOneByParams(teachId);
        int roleId = userRoleRefEntity.getRole_id();
        if (roleId == 10003) {
            List<ClassEntity> classEntityList = classMapper.queryClassByTeachId(teachId);
            for (ClassEntity classEntity : classEntityList) {
                ClassVo classVo = new ClassVo();
                BeanUtils.copyProperties(classEntity, classVo);
                UserEntity user = userMapper.selectById(classEntity.getTeach_id());
                classVo.setTeach_name(user.getUser_name());
                classVos.add(classVo);
            }
            return classVos;

        } else {
            List<ClassEntity> classEntityList = classMapper.selectList(null);
            for (ClassEntity classEntity : classEntityList) {
                ClassVo classVo = new ClassVo();
                BeanUtils.copyProperties(classEntity, classVo);
                UserEntity user = userMapper.selectById(classEntity.getTeach_id());
                classVo.setTeach_name(user.getUser_name());
                classVos.add(classVo);
            }
            return classVos;

        }

    }

    @Override
    public Result createClassInfo(ClassVo classVo) {
        Result result = new Result();
        //验证参数
        if (null == classVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建班级模型
        ClassEntity classEntity = new ClassEntity();

        //复制属性赋值给classEntity对象
        BeanUtils.copyProperties(classVo, classEntity);
        UserEntity user = userMapper.selectById(classVo.getTeach_id());

        classEntity.setTeach_name(user.getUser_name());
        classEntity.setUpdate_time(new Date());
        classEntity.setCreate_time(new Date());

        try {

            //创建资源类别表信息
            classMapper.insert(classEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    @Override
    public Result updateClassInfo(int class_id, ClassVo classVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(class_id))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        ClassEntity classEntity = classMapper.selectById(class_id);
        classEntity.setClass_name(classVo.getClass_name());
        classEntity.setTeach_id(classVo.getTeach_id());
        classEntity.setUpdate_time(new Date());

        try{
            classMapper.updateById(classEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    @Override
    public Result deleteClassInfo(int[] classIds) {
        Result result = new Result();
        // 验证参数
        if (classIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int classId : classIds) {

                //删除资源类别表信息
                classMapper.deleteById(classId);

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
    public Result updateClassStatusById(int classId, int status) {

        Result result = new Result();

        ClassEntity classEntity = classMapper.selectById(classId);

        classEntity.setStatus(status);
        try {
            classMapper.updateById(classEntity);
            if (1==status) {
                result.setMessage(MESSAGE_PARAM_STATUS_START);
            } else if(2==status){
                result.setMessage(MESSAGE_PARAM_STATUS_STOP);
            }else{
                //
            }

        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }

        return result;
    }

    @Override
    public List<UserVo> queryUsersByClassId(int classId) {
        List<UserVo> userVos = new ArrayList<>();
        List<ClassStudentEntity> classStudentEntities = classStudentMapper.selectClassStuByParams(classId);
        for (ClassStudentEntity classStudentEntitie : classStudentEntities) {
            UserVo userVo = new UserVo();
            UserEntity user = userMapper.selectById(classStudentEntitie.getStu_id());

            BeanUtils.copyProperties(user, userVo);
            userVos.add(userVo);
        }

        return userVos;
    }

    @Override
    public Result deleteClassStuInfo(int classId, int[] stuIds) {
        Result result = new Result();
        // 验证参数
        if (stuIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int stuId : stuIds) {
                Map<String, Object> params = new HashMap<>();
                params.put("class_id", classId);
                params.put("stu_id", stuId);
                //删除学员试卷关联表信息
                classStudentMapper.deleteByMap(params);

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
    public Result createClassStuInfos(int classId, int[] stuIds) {

        Result result = new Result();
        // 验证参数
        if (stuIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("class_id", classId);
            List<ClassStudentEntity> classStudentEntities = classStudentMapper.selectByMap(params);

            if(0 == classStudentEntities.size()){
                //若集合没有元素，则直接创建
                for (int stuId : stuIds) {
                    ClassStudentEntity classStudentEntity = new ClassStudentEntity();
                    classStudentEntity.setClass_id(classId);
                    classStudentEntity.setStu_id(stuId);

                    //创建学员试卷关联表信息
                    classStudentMapper.insert(classStudentEntity);
                }
                result.setSuccess(true);
                result.setMessage(MESSAGE_CREATE_SUCCESS);
            }else{
                //若集合存在元素。则需要判断前端传入的元素是否已经存在
                //定义一个集合接收数据库的stuId
                List<Integer> stuIdList = new ArrayList<>();
                for (ClassStudentEntity classStudentEntity : classStudentEntities) {
                    stuIdList.add(classStudentEntity.getStu_id());
                }

                for (int stuId : stuIds) {
                    //判断传入的stuId是否已经存在，若存在则忽略创建
                    if(stuIdList.contains(stuId)){

                        //忽略创建

                    }else{
                        ClassStudentEntity classStudentEntity = new ClassStudentEntity();
                        classStudentEntity.setClass_id(classId);
                        classStudentEntity.setStu_id(stuId);

                        //创建学员试卷关联表信息
                        classStudentMapper.insert(classStudentEntity);
                    }
                }
                result.setSuccess(true);
                result.setMessage(MESSAGE_CREATE_SUCCESS);
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }
}
