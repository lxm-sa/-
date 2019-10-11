package com.yb.managemodule.service;

import com.yb.base.vo.CategoryVo;
import com.yb.base.vo.Result;

import java.util.List;

/**
 * 资源分类服务接口类
 * Created by Administrator on 2019/8/19.
 */

public interface ICategoryService {


    /**
     * 查询资源类别列表，分页查询
     * @return
     */
    List<CategoryVo> queryPageCategoryInfo();

    /**
     * 创建资源类别信息
     * @param categoryVo
     * @return
     */
    Result createCategoryInfo(CategoryVo categoryVo);

    /**
     * 根据主键Id修改类别内容
     * @param category_id
     * @param categoryVo
     * @return
     */
    Result updateCategoryInfo(int category_id, CategoryVo categoryVo);


    /**
     * 删除资源类别，支持批量删除
     * @param categoryIds
     * @return
     */
    Result deleteCategoryInfo(int[] categoryIds);

}
