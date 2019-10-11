package com.yb.learningmodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.BarcodeMapper;
import com.yb.base.mapper.ContentPartsMapper;
import com.yb.base.mapper.PartsMapper;
import com.yb.base.mapper.StudyMapper;
import com.yb.base.pojo.BarcodeEntity;
import com.yb.base.pojo.PartsEntity;
import com.yb.base.pojo.StudyEntity;
import com.yb.learningmodule.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mayn on 2019/9/2.
 */
@Service
public class StudyServiceImpl implements StudyService{
    @Autowired
    private BarcodeMapper barcodeMapper;
    @Autowired
    private PartsMapper partMapper;
    @Autowired
    private ContentPartsMapper contentsPartMapper;
    @Autowired
    private  StudyMapper studyMapper;
    @Override
    public Map<String, Object> selelctPartShow(String barcode_id) {
       Map<String,Object> map= new HashMap<>();
        BarcodeEntity barcodeEntity = barcodeMapper.selectById(barcode_id);
        Integer part_id = barcodeEntity.getPart_id();
        PartsEntity partsEntity = partMapper.selectById(part_id);
        List list12= new ArrayList();
        List list22= new ArrayList();
        if(partsEntity!=null){
            String part_present = partsEntity.getPart_present();
            String[] str = part_present.split("\r\n");

             for (int i=0;i<str.length;i++){
                 list12.add(str[i]);
             }
            String part_principle = partsEntity.getPart_principle();
            String[] principle = part_principle.split("\r\n");

            for (int i=0;i<principle.length;i++){
                list22.add(principle[i]);
            }


        }
        map.put("str",list12);
        map.put("principle",list22);
        map.put("part",partsEntity);
        map.put("barcode",barcodeEntity);
        map.put("sub_id",barcodeEntity.getSub_id());
        List<Map<String,Object>> partList=contentsPartMapper.selectByPart_id(part_id);
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String, Object> map5 = new HashMap<>();
        for(Map<String,Object> map1:partList) {
            Map<String, Object> map2 = new HashMap<>();
            if (map5.get(map1.get("pro_id")+"")==null) {
                for (Map<String, Object> map3 : partList) {
                    if ((map1.get("pro_id")).equals(map3.get("pro_id"))) {
                        if (map2.get("pro_id") != null) {
                            String content_name = (String) map2.get("content_name");
                            String content_name1 = (String) map3.get("content_name");
                            map2.put("content_name", content_name + ";" + content_name1);
                        } else {
                            map2.put("content_name", (String) map3.get("content_name"));
                            map2.put("pro_name", (String) map3.get("pro_name"));
                            map2.put("pro_id", (Integer) map3.get("pro_id"));
                            map5.put(map1.get("pro_id") + "", (Integer) map3.get("pro_id"));
                        }
                    } else {
                        continue;
                    }
                }
                list.add(map2);
            }

        }
        map.put("list",list);
        return map;
    }

    @Override
    public int insertStudy(int user_id,String barcode_id) {
        Wrapper wrapper= new EntityWrapper();
        wrapper.eq("user_id",user_id);
        List<StudyEntity> list = studyMapper.selectList(wrapper);
        for (StudyEntity study:list){
            if (study.getBarcode_id().equals(barcode_id)){
                return 0;
            }
        }
        StudyEntity studyEntity= new StudyEntity();
        studyEntity.setUser_id(user_id);
        studyEntity.setBarcode_id(barcode_id);
        studyEntity.setCreate_time(new Date());

      Integer number = studyMapper.insert(studyEntity);
        return number;
    }
}
