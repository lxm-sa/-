package com.yb.managemodule.service.impl;

import com.yb.base.mapper.BarcodeMapper;
import com.yb.base.mapper.CategoryMapper;
import com.yb.base.pojo.CategoryEntity;
import com.yb.base.vo.CategoryVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.ICategoryService;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2019/8/19.
 */
@Service
public class CategoryService implements ICategoryService {

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
    private CategoryMapper categoryMapper;
    @Autowired
    private BarcodeMapper barcodeMapper;

    @Override
    public List<CategoryVo> queryPageCategoryInfo() {
        List<CategoryVo> categoryVos = new ArrayList<>();
        List<CategoryEntity> categoryEntityList = categoryMapper.selectList(null);
        for (CategoryEntity categoryEntity : categoryEntityList) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(categoryEntity, categoryVo);
            categoryVos.add(categoryVo);
        }
        return categoryVos;
    }

    @Override
    public Result createCategoryInfo(CategoryVo categoryVo) {
        Result result = new Result();
        //验证参数
        if (null == categoryVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建资源类别模型
        CategoryEntity categoryEntity = new CategoryEntity();

        //复制属性赋值给categoryEntity对象
        BeanUtils.copyProperties(categoryVo, categoryEntity);

        try {

            //创建资源类别表信息
            categoryMapper.insert(categoryEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    @Override
    public Result updateCategoryInfo(int category_id, CategoryVo categoryVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(category_id))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        CategoryEntity categoryEntity = categoryMapper.selectById(category_id);
        categoryEntity.setCategory_name(categoryVo.getCategory_name());
        categoryEntity.setRemark(categoryVo.getRemark());

        try{
            categoryMapper.updateById(categoryEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }


    @Transactional
    @Override
    public Result deleteCategoryInfo(int[] categoryIds) {
        Result result = new Result();
        // 验证参数
        if (categoryIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (int categoryId : categoryIds) {
                //删除关联的条码信息
                Map map = new HashMap();
                map.put("category_id",categoryId);
                barcodeMapper.deleteByMap(map);

                //删除资源类别表信息
                categoryMapper.deleteById(categoryId);

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
