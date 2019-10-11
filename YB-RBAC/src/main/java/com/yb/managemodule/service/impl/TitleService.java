package com.yb.managemodule.service.impl;

import com.yb.base.mapper.AnswerMapper;
import com.yb.base.mapper.TitleMapper;
import com.yb.base.mapper.TitleTypeMapper;
import com.yb.base.pojo.AnswerEntity;
import com.yb.base.pojo.TitleEntity;
import com.yb.base.pojo.TitleTypeEntity;
import com.yb.base.vo.*;
import com.yb.managemodule.service.ITitleService;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 题库管理服务实现类
 * Created by Administrator on 2019/8/22.
 */
@Service
public class TitleService implements ITitleService {
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

    @Autowired
    private TitleTypeMapper titleTypeMapper;
    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private AnswerMapper answerMapper;

    /**
     * 查询题型列表，支持分页查询
     * @return
     */
    @Override
    public List<TitleTypeVo> queryPageTitleTypeInfo() {
        List<TitleTypeVo> testTypeVos = new ArrayList<>();
        List<TitleTypeEntity> titleTypeEntityList= titleTypeMapper.selectList(null);
        for (TitleTypeEntity titleTypeEntity : titleTypeEntityList) {
            TitleTypeVo testTypeVo = new TitleTypeVo();
            BeanUtils.copyProperties(titleTypeEntity, testTypeVo);
            testTypeVos.add(testTypeVo);
        }
        return testTypeVos;
    }

    /**
     * 创建题型
     * @param titleTypeVo
     * @return
     */
    @Override
    public Result createTitleTypeInfo(TitleTypeVo titleTypeVo) {

        Result result = new Result();
        //验证参数
        if (null == titleTypeVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建资源类别模型
        TitleTypeEntity titleTypeEntity = new TitleTypeEntity();

        //复制属性赋值给titleTypeEntity对象
        BeanUtils.copyProperties(titleTypeVo, titleTypeEntity);

        try {
            titleTypeEntity.setUpdate_time(new Date());
            titleTypeEntity.setCreate_time(new Date());
            //创建题型表信息
            titleTypeMapper.insert(titleTypeEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    /**
     * 修改题型信息
     * @param titleTypeId
     * @param titleTypeVo
     * @return
     */
    @Override
    public Result updateTitleTypeInfo(int titleTypeId,TitleTypeVo titleTypeVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(titleTypeId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        TitleTypeEntity titleTypeEntity = titleTypeMapper.selectById(titleTypeId);
        titleTypeEntity.setTitle_type_name(titleTypeVo.getTitle_type_name());
        titleTypeEntity.setUpdate_time(new Date());

        try{
            titleTypeMapper.updateById(titleTypeEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    /**
     * 删除题型，支持批量删除
     * @param titleTypeIds
     * @return
     */
    @Override
    public Result deleteTitleTypeInfo(int[] titleTypeIds) {
        Result result = new Result();
        // 验证参数
        if (titleTypeIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int titleTypeId : titleTypeIds) {
                //先删除题目表信息，逻辑删除
                Map map = new HashMap<>();
                map.put("title_type_id",titleTypeId);
                List<TitleEntity> titlelist = titleMapper.selectByMap(map);
                for (TitleEntity title : titlelist) {
                    title.setIsdelete(1);
                    titleMapper.updateById(title);
                }

                //删除资源类别表信息
                titleTypeMapper.deleteById(titleTypeId);


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
    public List<TitleVo> queryPageTitleInfo() {
        List<TitleVo> titleVos = new ArrayList<>();
        List<TitleEntity> titleEntityList = titleMapper.selectTitleAll();
        for (TitleEntity titleEntity : titleEntityList) {
            TitleVo testVo = new TitleVo();
            BeanUtils.copyProperties(titleEntity, testVo);
            titleVos.add(testVo);
        }
        return titleVos;
    }

    @Override
    public List<TitleVo> queryPageTestTitleInfo() {
        List<TitleVo> titleVos = new ArrayList<>();
        Set<TitleEntity> titleEntityList = titleMapper.selectTitleList();
        for (TitleEntity titleEntity : titleEntityList) {
            TitleVo testVo = new TitleVo();
            BeanUtils.copyProperties(titleEntity, testVo);
            titleVos.add(testVo);
        }
        return titleVos;
    }


    @Override
    public List<TitleVo> queryPageTitleByParams(String titleContent,String titleTypeName) {
        List<TitleVo> titleVos = new ArrayList<>();
        List<TitleEntity> titleEntityList = titleMapper.selectTitleListByParams(titleContent,titleTypeName);
        for (TitleEntity titleEntity : titleEntityList) {
            TitleVo testVo = new TitleVo();
            BeanUtils.copyProperties(titleEntity, testVo);
            titleVos.add(testVo);
        }
        return titleVos;
    }

    @Override
    public Result createTitleInfo(TitleVo titleVo) {
        Result result = new Result();
        //验证参数
        if (null == titleVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建资源类别模型
        TitleEntity titleEntity = new TitleEntity();

        //复制属性赋值给titleEntity对象
        BeanUtils.copyProperties(titleVo, titleEntity);

        try {
            titleEntity.setIsdelete(0);
            titleEntity.setCreate_time(new Date());
            titleEntity.setUpdate_time(new Date());
            //创建题型表信息
            titleMapper.insert(titleEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    @Override
    public Result updateTitleInfo(int titleId, TitleVo titleVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(titleId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }

        TitleEntity titleEntity = titleMapper.selectById(titleId);
        titleEntity.setTitle_content(titleVo.getTitle_content());
        //得到题型编号
        TitleTypeEntity titleTypeEntity = titleTypeMapper.selectTitleTypeByName(titleVo.getTitle_type_name());
        titleEntity.setTitle_type_id(titleTypeEntity.getTitle_type_id());

        titleEntity.setUpdate_time(new Date());

        try{
            titleMapper.updateById(titleEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    @Override
    public Result deleteTitleInfo(int[] titleIds) {
        Result result = new Result();
        // 验证参数
        if (titleIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int titleId : titleIds) {
                //先删除答案相关信息
                Map map = new HashMap();
                map.put("title_id",titleId);
                List<AnswerEntity> answerlist = answerMapper.selectByMap(map);
                for (AnswerEntity answerEntity : answerlist) {
                    answerMapper.deleteById(answerEntity.getAnswer_id());
                }

                //删除题目表信息
                TitleEntity titleEntity = titleMapper.selectById(titleId);
                titleEntity.setIsdelete(1);

                titleMapper.updateById(titleEntity);


            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }


    /**************************************************答案管理*********************************************************************/

    /**
     * 分页查询答案列表信息
     * @return
     */
    @Override
    public List<AnswerVo> queryPageAnswerInfo() {
        List<AnswerVo> answerVos = new ArrayList<>();
        List<AnswerEntity> answerEntityList = answerMapper.selectAnswerAll();
        for (AnswerEntity answerEntity : answerEntityList) {
            AnswerVo answerVo = new AnswerVo();
            BeanUtils.copyProperties(answerEntity, answerVo);
            answerVos.add(answerVo);
        }
        return answerVos;
    }

    /**
     * 创建答案表信息
     * @param
     * @return
     */
    public Result createAnswerInfo(AnswerListVo answerListVo) {
        Result result = new Result();
        //验证参数
        if (null == answerListVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }
        try {
            String partIdStr = answerListVo.getPartId();
            String[] partIds = partIdStr.split(",");
            for (String partId : partIds) {
                //构建答案模型
                AnswerEntity answerEntity = new AnswerEntity();
                answerEntity.setTitle_id(answerListVo.getTitle_id());
                int part_id = Integer.parseInt(partId);
                answerEntity.setPart_id(part_id);
                answerEntity.setRemark(answerListVo.getRemark());
                answerEntity.setCreate_time(new Date());
                answerEntity.setUpdate_time(new Date());

                //判断答案表里面是否已经存在该条数据，若不存在则创建
                Map<String, Object> params = new HashMap<>();
                params.put("title_id", answerListVo.getTitle_id());
                params.put("part_id", part_id);
                List<AnswerEntity> answerEntities = answerMapper.selectByMap(params);
                if(answerEntities.size()==0){
                    //创建答案表信息
                    answerMapper.insert(answerEntity);

                    result.setMessage(MESSAGE_CREATE_SUCCESS);
                }else{
                    //忽略
                }


            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }


        return result;
    }

    /**
     * 修改答案信息
     * @param answerId
     * @param answerVo
     * @return
     */
    @Override
    public Result updateAnswerInfo(int answerId, AnswerVo answerVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(answerId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }

        AnswerEntity answerEntity = answerMapper.selectById(answerId);
        answerEntity.setRemark(answerVo.getRemark());
        answerEntity.setPart_id(answerVo.getPart_id());
//        answerEntity.setTitle_id(answerVo.getTitle_id());

        answerEntity.setUpdate_time(new Date());

        try{
            answerMapper.updateById(answerEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    /**
     * 删除答案表信息，支持批量删除
     * @param answerIds
     * @return
     */
    @Override
    public Result deleteAnswerInfo(int[] answerIds) {

        Result result = new Result();
        // 验证参数
        if (answerIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int answerId : answerIds) {
                //删除答案表信息
                answerMapper.deleteById(answerId);

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
