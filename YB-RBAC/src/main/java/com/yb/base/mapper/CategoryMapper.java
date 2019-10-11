package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2019/8/19.
 */

@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

    @Select("select * from category where category_name = #{categoryName}")
    CategoryEntity selectCategoryByParam(@Param("categoryName") String categoryName);

}
