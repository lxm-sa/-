package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.yb.base.vo.BarcodeVo;
import com.yb.base.vo.LearnSourceVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IBarcodeService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/20.
 */


@RestController
@RequestMapping(value = "/manage/barcode")
@Log4j
public class BarcodeController {

    private final static Logger logger = Logger.getLogger(BarcodeController.class);


    @Autowired
    private IBarcodeService barcodeService;

    /**
     * 分页查询条码列表
     * @param page
     * @return
     */
    @GetMapping("/queryPageBarcodes")
    @ResponseBody
    public Object queryPageBarcodes(Page page,Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber){

        PageHelper.setPagination(page);
        List<LearnSourceVo> learnSourceVos = barcodeService.queryPageBarcodeInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (learnSourceVos.size()<a){
            a=learnSourceVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(learnSourceVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(learnSourceVos.size());
        return page;
    }

    @GetMapping("/queryPageBarcodeByParam")
    @ResponseBody
    public Object queryPageBarcodeByParam(Page page,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,@RequestParam(value = "category_id", required =
            true)int categoryId){

        PageHelper.setPagination(page);
        List<LearnSourceVo> learnSourceVos = barcodeService.queryPageBarcodeByParam(categoryId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (learnSourceVos.size()<a){
            a=learnSourceVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(learnSourceVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(learnSourceVos.size());
        return page;
    }

    /**
     * 创建条码信息
     * @param barcodeVo
     * @return
     */
    @RequestMapping(value = "/createBarcodeInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createBarcodeInfo(BarcodeVo barcodeVo){

        Result result = barcodeService.createBarcodeInfo(barcodeVo);

        return result;
    }

    /**
     * 修改条码信息
     * @param barcodeId
     * @param barcodeVo
     * @return
     */
    @RequestMapping(value = "/updateBarcodeInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateBarcodeInfoById(@RequestParam("barcode_id")String barcodeId, BarcodeVo barcodeVo){

        Result result =barcodeService.updateBarcodeInfo(barcodeId,barcodeVo);

        return result;
    }


    @RequestMapping(value = "/updateBarcodeByParams", method = RequestMethod.POST)
    @ResponseBody
    public Result updateBarcodeByParams(@RequestParam("barcode_id")String barcodeId, BarcodeVo barcodeVo){

        Result result =barcodeService.updateBarcodeByParams(barcodeId,barcodeVo);

        return result;
    }


    /**
     * 删除条码，支持批量删除
     * @param barcodeIds
     * @return
     */
    @RequestMapping(value = "/deleteBarcodeInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteBarcodeInfos(@RequestParam("barcodeIds[]")String[] barcodeIds){

        Result result =barcodeService.deleteBarcodeInfo(barcodeIds);

        return result;
    }


}
