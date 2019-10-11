package com.yb.managemodule.service;

import com.yb.base.vo.RoleVo;

import java.util.List;

/**
 * Created by Administrator on 2019/8/9.
 */
public interface IRoleService {

    /**
     * 分页查询，角色列表
     * @return
     */
    List<RoleVo> queryPageRoleInfo();

}
