package com.yb.managemodule.service.impl;

import com.yb.base.mapper.BarcodeMapper;
import com.yb.base.mapper.CategoryMapper;
import com.yb.base.mapper.PartsMapper;
import com.yb.base.mapper.SubjectMapper;
import com.yb.base.pojo.BarcodeEntity;
import com.yb.base.pojo.CategoryEntity;
import com.yb.base.pojo.PartsEntity;
import com.yb.base.pojo.SubjectEntity;
import com.yb.base.util.BarcodeUtils;
import com.yb.base.vo.BarcodeVo;
import com.yb.base.vo.LearnSourceVo;
import com.yb.base.vo.Result;
import com.yb.managemodule.service.IBarcodeService;
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
public class BarcodeService implements IBarcodeService {
    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【BarcodeVo】不能为空";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";

   /* private  BarcodeUtils barcodeUtils;*/

    @Autowired
    private BarcodeMapper barcodeMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private PartsMapper partsMapper;

    @Override
    public List<LearnSourceVo> queryPageBarcodeInfo() {

        List<LearnSourceVo> learnSourceVos = new ArrayList<>();

        List<BarcodeEntity> barcodeList = barcodeMapper.selectList(null);
        for (BarcodeEntity barcode : barcodeList) {
            if((barcode.getBarcode_id()).startsWith("166")){
                List<BarcodeEntity> barcodeEntityList = barcodeMapper.selectBarcode(barcode.getBarcode_id());
                Set<String> barcodeIdList = new HashSet<String>();
                for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                    barcodeIdList.add(barcodeEntity.getBarcode_id());
                }
                for (String barcodeId : barcodeIdList) {

                    LearnSourceVo learnSourceVo = new LearnSourceVo();
                    //定义StringBuffer类型的字符接收项目名称和内容名称
                    StringBuffer proNameBuf = new StringBuffer();    //项目名
                    StringBuffer contentNameBuf = new StringBuffer();   //项目内容
                    int i = 0;
                    //循环遍历barcodeEntityList
                    for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                        //判断Id相同的进行整合添加至LearnSourceVo对象
                        if(barcodeId.equals(barcodeEntity.getBarcode_id())){
                            i++;
                            //将项目内容拼接至contentNameBuf
                            contentNameBuf.append(i+"."+barcodeEntity.getContent_name()+"      ");
                            //将项目名拼接至proNameBuf
                            proNameBuf.append(i+"."+barcodeEntity.getPro_name()+"      ");

                            learnSourceVo.setBarcode_id(barcodeId);
                            learnSourceVo.setBarcode_url(barcodeEntity.getBarcode_url());
                            learnSourceVo.setCategory_id(barcodeEntity.getCategory_id());
                            learnSourceVo.setCategory_name(barcodeEntity.getCategory_name());
                            learnSourceVo.setPosition_url(barcodeEntity.getPosition_url());
                            learnSourceVo.setContentName(contentNameBuf.toString());
                            learnSourceVo.setProName(proNameBuf.toString());

                            learnSourceVo.setPart_id(barcodeEntity.getPart_id());
                            learnSourceVo.setPart_name(barcodeEntity.getPart_name());
                            learnSourceVo.setPart_position(barcodeEntity.getPart_position());
                            learnSourceVo.setPart_principle(barcodeEntity.getPart_principle());
                            learnSourceVo.setPart_present(barcodeEntity.getPart_present());
                            learnSourceVo.setPart_spec(barcodeEntity.getPart_spec());
                            learnSourceVo.setPart_url(barcodeEntity.getPart_url());
                            learnSourceVo.setPart_number(barcodeEntity.getPart_number());
                            learnSourceVo.setSub_id(barcodeEntity.getSub_id());
                            learnSourceVo.setSub_name(barcodeEntity.getSub_name());

                        }else{


                            //
                        }

                    }

                    learnSourceVos.add(learnSourceVo);

                }

            }else{
                BarcodeEntity barcodeEntity = barcodeMapper.queryBarcode(barcode.getBarcode_id());
                LearnSourceVo learnSourceVo = new LearnSourceVo();
                learnSourceVo.setBarcode_id(barcodeEntity.getBarcode_id());
                learnSourceVo.setBarcode_url(barcodeEntity.getBarcode_url());
                learnSourceVo.setCategory_id(barcodeEntity.getCategory_id());
                learnSourceVo.setCategory_name(barcodeEntity.getCategory_name());
                learnSourceVo.setPart_id(barcodeEntity.getPart_id());
                learnSourceVo.setPart_name(barcodeEntity.getPart_name());
                learnSourceVo.setPart_position(barcodeEntity.getPart_position());
                learnSourceVo.setPart_principle(barcodeEntity.getPart_principle());
                learnSourceVo.setPart_present(barcodeEntity.getPart_present());
                learnSourceVo.setPart_spec(barcodeEntity.getPart_spec());
                learnSourceVo.setPart_url(barcodeEntity.getPart_url());
                learnSourceVo.setPart_number(barcodeEntity.getPart_number());
                learnSourceVo.setSub_id(barcodeEntity.getSub_id());
                learnSourceVo.setSub_name(barcodeEntity.getSub_name());
                learnSourceVo.setPosition_url(barcodeEntity.getPosition_url());
                learnSourceVos.add(learnSourceVo);
            }

        }

       /* List<BarcodeEntity> barcodeEntityList = barcodeMapper.selectBarcodeAll();
        Set<String> barcodeIdList = new HashSet<String>();
        for (BarcodeEntity barcodeEntity : barcodeEntityList) {
            barcodeIdList.add(barcodeEntity.getBarcode_id());
        }
        for (String barcodeId : barcodeIdList) {

            LearnSourceVo learnSourceVo = new LearnSourceVo();
            //定义StringBuffer类型的字符接收项目名称和内容名称
            StringBuffer proNameBuf = new StringBuffer();    //项目名
            StringBuffer contentNameBuf = new StringBuffer();   //项目内容
            int i = 0;
            //循环遍历barcodeEntityList
            for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                //判断Id相同的进行整合添加至LearnSourceVo对象
                if(barcodeId.equals(barcodeEntity.getBarcode_id())){
                    i++;
                    //将项目内容拼接至contentNameBuf
                    contentNameBuf.append(i+"."+barcodeEntity.getContent_name()+"      ");
                    //将项目名拼接至proNameBuf
                    proNameBuf.append(i+"."+barcodeEntity.getPro_name()+"      ");

                    learnSourceVo.setBarcode_id(barcodeId);
                    learnSourceVo.setBarcode_url(barcodeEntity.getBarcode_url());
                    learnSourceVo.setCategory_id(barcodeEntity.getCategory_id());
                    learnSourceVo.setCategory_name(barcodeEntity.getCategory_name());

                    learnSourceVo.setContentName(contentNameBuf.toString());
                    learnSourceVo.setProName(proNameBuf.toString());

                    learnSourceVo.setPart_id(barcodeEntity.getPart_id());
                    learnSourceVo.setPart_name(barcodeEntity.getPart_name());
                    learnSourceVo.setPart_position(barcodeEntity.getPart_position());
                    learnSourceVo.setPart_principle(barcodeEntity.getPart_principle());
                    learnSourceVo.setPart_present(barcodeEntity.getPart_present());
                    learnSourceVo.setPart_spec(barcodeEntity.getPart_spec());
                    learnSourceVo.setPart_url(barcodeEntity.getPart_url());
                    learnSourceVo.setPart_number(barcodeEntity.getPart_number());
                    learnSourceVo.setSub_id(barcodeEntity.getSub_id());
                    learnSourceVo.setSub_name(barcodeEntity.getSub_name());

                }else{

                    //
                }

            }

            learnSourceVos.add(learnSourceVo);

        }
*/

        return learnSourceVos;
    }

    public List<LearnSourceVo> queryPageBarcodeByParam(int categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", categoryId);
        List<LearnSourceVo> learnSourceVos = new ArrayList<>();
        List<BarcodeEntity> barcodeList = barcodeMapper.selectByMap(params);
        for (BarcodeEntity barcode : barcodeList) {
            if((barcode.getBarcode_id()).startsWith("166")){
                List<BarcodeEntity> barcodeEntityList = barcodeMapper.selectBarcode(barcode.getBarcode_id());
                Set<String> barcodeIdList = new HashSet<String>();
                for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                    barcodeIdList.add(barcodeEntity.getBarcode_id());
                }
                for (String barcodeId : barcodeIdList) {

                    LearnSourceVo learnSourceVo = new LearnSourceVo();
                    //定义StringBuffer类型的字符接收项目名称和内容名称
                    StringBuffer proNameBuf = new StringBuffer();    //项目名
                    StringBuffer contentNameBuf = new StringBuffer();   //项目内容
                    int i = 0;
                    //循环遍历barcodeEntityList
                    for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                        //判断Id相同的进行整合添加至LearnSourceVo对象
                        if(barcodeId.equals(barcodeEntity.getBarcode_id())){
                            i++;
                            //将项目内容拼接至contentNameBuf
                            contentNameBuf.append(i+"."+barcodeEntity.getContent_name()+"      ");
                            //将项目名拼接至proNameBuf
                            proNameBuf.append(i+"."+barcodeEntity.getPro_name()+"      ");

                            learnSourceVo.setBarcode_id(barcodeId);
                            learnSourceVo.setBarcode_url(barcodeEntity.getBarcode_url());
                            learnSourceVo.setCategory_id(barcodeEntity.getCategory_id());
                            learnSourceVo.setCategory_name(barcodeEntity.getCategory_name());

                            learnSourceVo.setContentName(contentNameBuf.toString());
                            learnSourceVo.setProName(proNameBuf.toString());

                            learnSourceVo.setPart_id(barcodeEntity.getPart_id());
                            learnSourceVo.setPart_name(barcodeEntity.getPart_name());
                            learnSourceVo.setPart_position(barcodeEntity.getPart_position());
                            learnSourceVo.setPart_principle(barcodeEntity.getPart_principle());
                            learnSourceVo.setPart_present(barcodeEntity.getPart_present());
                            learnSourceVo.setPart_spec(barcodeEntity.getPart_spec());
                            learnSourceVo.setPart_url(barcodeEntity.getPart_url());

                            learnSourceVo.setPosition_url(barcodeEntity.getPosition_url());
                            learnSourceVo.setPart_number(barcodeEntity.getPart_number());
                            learnSourceVo.setSub_id(barcodeEntity.getSub_id());
                            learnSourceVo.setSub_name(barcodeEntity.getSub_name());

                        }else{
                            //
                        }
                    }

                    learnSourceVos.add(learnSourceVo);
                }

            }else{
                BarcodeEntity barcodeEntity = barcodeMapper.queryBarcode(barcode.getBarcode_id());
                LearnSourceVo learnSourceVo = new LearnSourceVo();
                learnSourceVo.setBarcode_id(barcodeEntity.getBarcode_id());
                learnSourceVo.setBarcode_url(barcodeEntity.getBarcode_url());
                learnSourceVo.setCategory_id(barcodeEntity.getCategory_id());
                learnSourceVo.setCategory_name(barcodeEntity.getCategory_name());
                learnSourceVo.setPart_id(barcodeEntity.getPart_id());
                learnSourceVo.setPart_name(barcodeEntity.getPart_name());
                learnSourceVo.setPart_position(barcodeEntity.getPart_position());
                learnSourceVo.setPart_principle(barcodeEntity.getPart_principle());
                learnSourceVo.setPart_present(barcodeEntity.getPart_present());
                learnSourceVo.setPart_spec(barcodeEntity.getPart_spec());
                learnSourceVo.setPosition_url(barcodeEntity.getPosition_url());
                learnSourceVo.setPart_url(barcodeEntity.getPart_url());
                learnSourceVo.setPart_number(barcodeEntity.getPart_number());
                learnSourceVo.setSub_id(barcodeEntity.getSub_id());
                learnSourceVo.setSub_name(barcodeEntity.getSub_name());
                learnSourceVos.add(learnSourceVo);
            }
        }
        return learnSourceVos;
    }
    @Override
    public Result createBarcodeInfo(BarcodeVo barcodeVo) {

            Result result = new Result();
            //验证参数
            if (null == barcodeVo) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_PARAM_OBJ_NULL);
                return result;
            }

            //构建科目模型
        BarcodeEntity barcodeEntity = new BarcodeEntity();

        String message=barcodeVo.getBarcode_id();

        //生成一维码条码编号及条形码图片
        BarcodeUtils.createBarcode(message,barcodeVo.getBarcode_url());



            //复制属性赋值给barcodeEntity对象
            BeanUtils.copyProperties(barcodeVo, barcodeEntity);
            Date date = new Date();
            try {

                //创建条码表信息
                barcodeMapper.insert(barcodeEntity);

                result.setMessage(MESSAGE_CREATE_SUCCESS);

            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage(MESSAGE_CREATE_FAIL);
            }

            return result;
    }

    @Override
    public Result updateBarcodeInfo(String barcodeId, BarcodeVo barcodeVo) {
        Result result = new Result();
        //验证参数
        if (null== barcodeId) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        BarcodeEntity barcodeEntity = barcodeMapper.selectById(barcodeId);
        //得到资源类别Id
        CategoryEntity categoryEntity = categoryMapper.selectCategoryByParam(barcodeVo.getCategory_name());
        barcodeEntity.setCategory_id(categoryEntity.getCategory_id());
        //得到科目id
        SubjectEntity subjectEntity = subjectMapper.selectSubjectOne(barcodeVo.getSub_name());

        barcodeEntity.setSub_id(subjectEntity.getSub_id());
        //得到部件Id

        barcodeEntity.setPart_id(barcodeVo.getPart_id());


        try{
            barcodeMapper.updateById(barcodeEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }

    @Override
    public Result updateBarcodeByParams(String barcodeId, BarcodeVo barcodeVo) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(barcodeId)) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }

        BarcodeEntity barcodeEntity = barcodeMapper.selectById(barcodeId);

        //得到科目id
        SubjectEntity subjectEntity = subjectMapper.selectSubjectOne(barcodeVo.getSub_name());

        barcodeEntity.setSub_id(subjectEntity.getSub_id());
        //得到部件Id

        barcodeEntity.setPart_id(barcodeVo.getPart_id());


        try{
            barcodeMapper.updateById(barcodeEntity);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }
    @Transactional
    @Override
    public Result deleteBarcodeInfo(String[] barcodeIds) {
        Result result = new Result();
        // 验证参数
        if (barcodeIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {


            for (String barcodeId : barcodeIds) {
                //删除条码表信息
                barcodeMapper.deleteById(barcodeId);

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
