package com.yb.learningmodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.*;
import com.yb.base.pojo.*;
import com.yb.base.vo.MistakeVo;
import com.yb.learningmodule.service.MistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/8/28.
 */
@Service
public class MistakeServiceImpl implements MistakeService{
    @Autowired
    private StuAnswerMapper stuAnswerMapper;
    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private TitleTypeMapper titleTypeMapper;
    @Autowired
    private PartsMapper partMapper;
    @Override
    public List<MistakeVo> selectMistakeList(int index,String type_id) {

        List<Map<String,Object>> stuAnswerEntities = stuAnswerMapper.selectAllpage((index-1)*8, 8,type_id);
        List mistakeList=new ArrayList();
        for (Map<String,Object> stu :stuAnswerEntities){
            MistakeVo mistakeVo = new MistakeVo();
            mistakeVo.setTitle_id((Integer) stu.get("title_id"));
            mistakeVo.setTitle_content((String) stu.get("title_content"));
            TitleTypeEntity titleType = titleTypeMapper.selectById((Integer) stu.get("title_type_id"));
            if(titleType!=null){
                mistakeVo.setTitle_type_name(titleType.getTitle_type_name());
            }else {
                mistakeVo.setTitle_type_name("未知");
            }

            Wrapper wrapper= new EntityWrapper();
            wrapper.eq("title_id",(Integer) stu.get("title_id"));
            List<AnswerEntity> answerList = answerMapper.selectList(wrapper);
            String str="";
            for ( AnswerEntity answer :answerList) {
                PartsEntity partsEntity = partMapper.selectById(answer.getPart_id());
                if (partsEntity != null) {
                    if (str.equals("")) {
                        str = partsEntity.getPart_name();
                        continue;
                    }
                    str = str + "," + partsEntity.getPart_name();
                }
            }
            mistakeVo.setPart_name(str);
            mistakeList.add(mistakeVo);
        }
        return mistakeList;
    }

    @Override
    public double selectMistakecount(String type_id) {
       int count= stuAnswerMapper.selectMistakecount(type_id);
      double count1 =Math.ceil(count/8.0);
      if (count1==0){
          count1=1;
      }
        return count1;
    }

    @Override
    public List<TitleTypeEntity> selectTitleTypeAll() {
        Wrapper wrapper = new EntityWrapper();
        wrapper.orderBy("title_type_id");
        List list = titleTypeMapper.selectList(wrapper);
        return list;
    }
}
