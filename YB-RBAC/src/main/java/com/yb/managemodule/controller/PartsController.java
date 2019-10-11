package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.util.UpdateUtil;
import com.yb.base.vo.PartsVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IPartsService;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzl on 2019/8/13.
 */

@RestController
@RequestMapping(value = "/manage/parts")
@Log4j
public class PartsController {
    private final static Logger LOGGER = Logger.getLogger(PartsController.class);

    @Autowired
    private IPartsService partsService;

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    /**
     * 分页查询部件列表
     * @param page
     * @return
     */
    @GetMapping("/queryPageParts")
    @ResponseBody
    public Object queryPageParts(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){


        List<PartsVo> partsVos = partsService.queryPagePartsInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (partsVos.size()<a){
            a=partsVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(partsVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(partsVos.size());
        return page;
    }

    /**
     * 根据部件名称模糊查询
     * @param page
     * @param partName
     * @return
     */
    @GetMapping("/queryPagePartsInfoByParams")
    @ResponseBody
    public Object queryPagePartsInfoByParams(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page,@RequestParam(value = "part_name")String partName){
        List<PartsVo> partsVos = partsService.queryPagePartsInfoByParams(partName);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (partsVos.size()<a){
            a=partsVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(partsVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(partsVos.size());
        return page;

    }

    /**
     * 创建部件信息
     * @param partsVo
     * @return
     */
    @RequestMapping(value = "/createPartsInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createPartsInfo(PartsVo partsVo){

        Result result = partsService.createPartsInfo(partsVo);

        return result;
    }

    /**
     * 根据Id修改部件信息
     * @param partId
     * @param partsVo
     * @return
     */
    @RequestMapping(value = "/updatePartsInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePartsInfoById(@RequestParam("part_id")int partId, PartsVo partsVo){

        Result result =partsService.updatePartsInfo(partId,partsVo);

        return result;
    }

    /**
     * 删除部件信息，支持批量删除
     * @param partIds
     * @return
     */
    @RequestMapping(value = "/deletePartInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deletePartInfos(@RequestParam("partIds[]")int[] partIds){

        Result result =partsService.deletePartsInfo(partIds);

        return result;
    }


    /**
     * 图片上传
     * @param file
     * @return
     */
   // @ApiImplicitParam(name = "file", value = "结构图", required = true, dataType = "org.springframework.web.multipart.MultipartFile")
    @PostMapping("/updatePhoto")
    public Result deleteSys(@RequestParam(value="part_url",required=false)MultipartFile file) {
        Result result = new Result();
        if(file==null) {
            result.setSuccess(false);
            result.setStatusCode(500);
            result.setMessage("上传图片为空");
            return result;
        }

        try {
            String photopath= new UpdateUtil().updatePhoto(file, fileUploadFolder);
           // String imgPath=fileUploadFolder+photopath;
            result.setSuccess(true);
            result.setStatusCode(200);
            result.setMessage(photopath);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatusCode(500);
            result.setMessage("图片上传失败");
            return result;
        }
    }

    @PostMapping("/updatePositionPhoto")
    public Result updatePositionPhoto(@RequestParam(value="position_url",required=false)MultipartFile file) {
        Result result = new Result();
        if(file==null) {
            result.setSuccess(false);
            result.setStatusCode(500);
            result.setMessage("上传图片为空");
            return result;
        }

        try {
            String photopath= new UpdateUtil().updatePhoto(file, fileUploadFolder);
            // String imgPath=fileUploadFolder+photopath;
            result.setSuccess(true);
            result.setStatusCode(200);
            result.setMessage(photopath);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatusCode(500);
            result.setMessage("图片上传失败");
            return result;
        }
    }
}
