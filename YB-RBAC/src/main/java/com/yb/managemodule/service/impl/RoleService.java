package com.yb.managemodule.service.impl;

import com.yb.base.mapper.RoleMapper;
import com.yb.base.pojo.RoleEntity;
import com.yb.base.vo.RoleVo;
import com.yb.managemodule.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/9.
 */

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleVo> queryPageRoleInfo() {

        List<RoleVo> roleVos = new ArrayList<>();

        List<RoleEntity> roleList = roleMapper.selectList(null);

        for (RoleEntity role : roleList) {

            RoleVo roleVo = new RoleVo();

            BeanUtils.copyProperties(role, roleVo);

            roleVos.add(roleVo) ;

        }


        return roleVos;
    }
}
