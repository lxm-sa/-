package com.yb.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yb.base.pojo.BarcodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/8/19.
 */
@Mapper
public interface BarcodeMapper extends BaseMapper<BarcodeEntity> {

    @Select("select b.*,c.category_name,p.*, pc.content_name, pt.pro_name,s.sub_name \n" +
            "from \n" +
            "barcode b inner join category c \n" +
            "on b.category_id = c.category_id \n" +
            "inner join \n" +
            "parts p  on b.part_id = p.part_id\n" +
            "inner join \n" +
            "content_parts cp on p.part_id = cp.part_id\n" +
            "inner join \n" +
            "project_content pc on cp.content_id = pc.content_id\n" +
            "inner join \n" +
            "project_train pt on pc.pro_id = pt.pro_id\n" +
            "inner join \n" +
            "subject s on pt.sub_id = s.sub_id where s.isdelete = 0 order by b.barcode_id DESC")
    List<BarcodeEntity> selectBarcodeAll();
    @Select("select b.*,c.category_name,p.*, pc.content_name, pt.pro_name,s.sub_name \n" +
            "from \n" +
            "barcode b inner join category c \n" +
            "on b.category_id = c.category_id \n" +
            "inner join \n" +
            "parts p  on b.part_id = p.part_id\n" +
            "inner join \n" +
            "content_parts cp on p.part_id = cp.part_id\n" +
            "inner join \n" +
            "project_content pc on cp.content_id = pc.content_id\n" +
            "inner join \n" +
            "project_train pt on pc.pro_id = pt.pro_id\n" +
            "inner join \n" +
            "subject s on pt.sub_id = s.sub_id where s.isdelete = 0 and b.barcode_id =#{barcodeId} order by b.barcode_id DESC")
    List<BarcodeEntity> selectBarcode(@Param("barcodeId") String barcodeId);

    @Select("select b.*,c.category_name,p.*,s.sub_name \n" +
            "from \n" +
            "barcode b inner join category c \n" +
            "on b.category_id = c.category_id \n" +
            "inner join \n" +
            "parts p  on b.part_id = p.part_id\n" +
            "inner join \n" +
            "subject s on b.sub_id = s.sub_id where s.isdelete = 0 and b.barcode_id =#{barcodeId} order by b.barcode_id DESC")
    BarcodeEntity queryBarcode(@Param("barcodeId") String barcodeId);

    @Select("select b.*,c.category_name,p.*, pc.content_name, pt.pro_name,s.sub_name \n" +
            "from \n" +
            "barcode b inner join category c \n" +
            "on b.category_id = c.category_id \n" +
            "inner join \n" +
            "parts p  on b.part_id = p.part_id\n" +
            "inner join \n" +
            "content_parts cp on p.part_id = cp.part_id\n" +
            "inner join \n" +
            "project_content pc on cp.content_id = pc.content_id\n" +
            "inner join \n" +
            "project_train pt on pc.pro_id = pt.pro_id\n" +
            "inner join \n" +
            "subject s on pt.sub_id = s.sub_id where s.isdelete = 0  and c.category_id =#{categoryId} order by b.barcode_id DESC")
    List<BarcodeEntity> selectBarcodeByParams(@Param("categoryId") int categoryId);

}
