package com.yb.managemodule.service.impl;

import com.yb.base.mapper.BarcodeMapper;
import com.yb.base.mapper.PartsMapper;
import com.yb.base.pojo.PartsEntity;
import com.yb.base.vo.PartsVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IPartsService;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2019/8/13.
 */

@Service
public class PartsService implements IPartsService {

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
   private PartsMapper partsMapper ;
    @Autowired
    private BarcodeMapper barcodeMapper;

    @Override
    public List<PartsVo> queryPagePartsInfo() {
        List<PartsVo> partsVos = new ArrayList<>();
        List<PartsEntity> partsList = partsMapper.selectList(null);
        for (PartsEntity partsEntity : partsList) {
            PartsVo partsVo = new PartsVo();
            BeanUtils.copyProperties(partsEntity, partsVo);
            partsVos.add(partsVo);
        }

        return partsVos;
    }

    @Override
    public List<PartsVo> queryPagePartsInfoByParams(String partName) {

        List<PartsVo> partsVos = new ArrayList<>();

        List<PartsEntity> partsEntities = partsMapper.queryPagePartsInfoByParams(partName);

        for (PartsEntity part : partsEntities) {

            PartsVo partsVo = new PartsVo();

            BeanUtils.copyProperties(part, partsVo);

            partsVos.add(partsVo);
        }

        return partsVos;
    }

    @Override
    public Result createPartsInfo(PartsVo partsVo) {
        Result result = new Result();
        //验证参数
        if (null == partsVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建科目模型
        PartsEntity partsEntity = new PartsEntity();

        //复制属性赋值给User对象
        BeanUtils.copyProperties(partsVo, partsEntity);
        Date date = new Date();
        try {
            partsEntity.setUpdate_time(date);

            partsEntity.setCreate_time(date);

            //创建科目表信息
            partsMapper.insert(partsEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    @Override
    public Result updatePartsInfo(int partId, PartsVo partsVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(partId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        PartsEntity partsEntity = partsMapper.selectById(partId);
        partsEntity.setPart_name(partsVo.getPart_name());
        partsEntity.setPart_position(partsVo.getPart_position());
        partsEntity.setPart_present(partsVo.getPart_present());
        partsEntity.setPart_principle(partsVo.getPart_principle());
        partsEntity.setPart_spec(partsVo.getPart_spec());
        partsEntity.setPart_url(partsVo.getPart_url());
        partsEntity.setPart_number(partsVo.getPart_number());
        partsEntity.setOrder_number(partsVo.getOrder_number());
        partsEntity.setCreate_time(new Date());
        partsEntity.setUpdate_time(new Date());
        partsEntity.setPosition_url(partsVo.getPosition_url());
        try{
            partsMapper.updateById(partsEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }


    @Transactional
    @Override
    public Result deletePartsInfo(int[] partIds) {
        Result result = new Result();
        // 验证参数
        if (partIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int partId : partIds) {
                //删除对应的条码信息
                Map map = new HashMap();
                map.put("part_id",partId);
                barcodeMapper.deleteByMap(map);
                //删除部件表信息
                partsMapper.deleteById(partId);

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
