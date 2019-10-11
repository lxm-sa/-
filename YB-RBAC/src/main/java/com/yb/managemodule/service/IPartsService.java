package com.yb.managemodule.service;

import com.yb.base.vo.PartsVo;
import com.yb.base.vo.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/8/13.
 */
public interface IPartsService {

    /**
     * 部件分页列表
     * @return
     */
    List<PartsVo> queryPagePartsInfo();

    /**
     * 根据部件名称模糊查询
     * @param partName
     * @return
     */
    List<PartsVo> queryPagePartsInfoByParams(String partName);

    /**
     * 创建部件信息
     * @param partsVo
     * @return
     */
    Result createPartsInfo(PartsVo partsVo);

    /**
     * 修改部件信息
     * @param partId
     * @param partsVo
     * @return
     */
    Result updatePartsInfo(int partId, PartsVo partsVo);

    /**
     * 删除部件信息，支持批量删除
     * @param partIds
     * @return
     */
    Result deletePartsInfo(int[] partIds);



}
