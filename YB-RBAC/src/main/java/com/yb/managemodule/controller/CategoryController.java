package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.vo.CategoryVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.ICategoryService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源类别管理控制类
 * Created by Administrator on 2019/8/19.
 */


@RestController
@RequestMapping(value = "/manage/category")
@Log4j
public class CategoryController {

    private final static Logger LOGGER = Logger.getLogger(CategoryController.class);

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/queryPageCategorys")
    @ResponseBody
    public Object queryPageCategorys(Page page,Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber){

        List<CategoryVo> categoryVos = categoryService.queryPageCategoryInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (categoryVos.size()<a){
            a=categoryVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(categoryVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(categoryVos.size());
        return page;
    }

    @GetMapping("/queryCategoryList")
    @ResponseBody
    public List<CategoryVo> queryCategoryList(){


        List<CategoryVo> categoryVos = categoryService.queryPageCategoryInfo();

        return categoryVos;
    }

    @RequestMapping(value = "/createCategoryInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createCategoryInfo(CategoryVo categoryVo){

        Result result = categoryService.createCategoryInfo(categoryVo);

        return result;
    }

    @RequestMapping(value = "/updateCategoryInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateCategoryInfo(@RequestParam("category_id")int category_id, CategoryVo categoryVo){

        Result result =categoryService.updateCategoryInfo(category_id,categoryVo);

        return result;
    }
    @RequestMapping(value = "/deleteCategoryInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCategoryInfo(@RequestParam("categoryIds[]")int[] categoryIds){

        Result result =categoryService.deleteCategoryInfo(categoryIds);

        return result;
    }



}
