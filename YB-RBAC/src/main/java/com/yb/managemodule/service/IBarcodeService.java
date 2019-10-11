package com.yb.managemodule.service;

import com.yb.base.vo.BarcodeVo;
import com.yb.base.vo.LearnSourceVo;
import com.yb.base.vo.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/8/19.
 */
public interface IBarcodeService {


    /**
     * 分页查询条码信息列表
     * @return
     */
    List<LearnSourceVo> queryPageBarcodeInfo();

    /**
     * 根据类别查询条码信息列表
     * @param categoryId
     * @return
     */
    List<LearnSourceVo> queryPageBarcodeByParam(int categoryId);


    /**
     * 创建条码信息
     * @param barcodeVo
     * @return
     */
    Result createBarcodeInfo(BarcodeVo barcodeVo);



    /**
     * 修改条码信息内容
     * @param barcodeId
     * @param barcodeVo
     * @return
     */
    Result updateBarcodeInfo(String barcodeId, BarcodeVo barcodeVo);


    /**
     * 修改条码信息内容
     * @param barcodeId
     * @param barcodeVo
     * @return
     */
    Result updateBarcodeByParams(String barcodeId, BarcodeVo barcodeVo);

    /**
     * 删除条码，支持批量删除
     * @param barcodeIds
     * @return
     */
    Result deleteBarcodeInfo(String[] barcodeIds);

}
