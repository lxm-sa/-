package com.yb.learningmodule.service;

import com.yb.base.pojo.TitleTypeEntity;
import com.yb.base.vo.MistakeVo;

import java.util.List;

/**
 * Created by mayn on 2019/8/28.
 */
public interface MistakeService {
    List<MistakeVo> selectMistakeList(int index,String type_id);

    double selectMistakecount(String type_id);

    List<TitleTypeEntity> selectTitleTypeAll();
}
