package com.yb.managemodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.ModularMapper;
import com.yb.base.mapper.PermissionMapper;
import com.yb.base.mapper.RoleMapper;
import com.yb.base.mapper.RolePermissionMapper;
import com.yb.base.pojo.*;
import com.yb.base.vo.ModularVo;
import com.yb.base.vo.RolePermissionVo;
import com.yb.managemodule.service.RoleManageService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mayn on 2019/8/21.
 */
@Service
public class RoleManageServiceImpl implements RoleManageService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ModularMapper modularMapper;
    @Override
    public List<RolePermissionVo> selectRoleList(int index) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.groupBy("role_id");
        RowBounds rowBounds= new RowBounds((index-1)*8,8);
        List<Map<String, Object> > maps = roleMapper.selectMapsPage(rowBounds,wrapper);
        List<RolePermissionVo> rolePerVos = new ArrayList<>();
        if (maps.size()>0) {
            for (Map<String, Object> map1 : maps) {
                RolePermissionVo rolePermissionVo = new RolePermissionVo();
                rolePermissionVo.setRole_id((Integer) map1.get("role_id"));
                rolePermissionVo.setRole_name((String) map1.get("role_name"));
                Wrapper wrapper1 = new EntityWrapper();
                wrapper1.eq("role_id",(Integer)map1.get("role_id") );
                wrapper1.groupBy(true,"permission_id");
                List<RolePermissionRefEntity> rolePermissionRefEntitys= rolePermissionMapper.selectList(wrapper1);
                List list= new ArrayList();
                for (RolePermissionRefEntity rolePermissionRefEntity:rolePermissionRefEntitys)
                {
                 list.add(rolePermissionRefEntity.getPermission_id());
                }
                rolePermissionVo.setPerIds(list);
                rolePerVos.add(rolePermissionVo);
            }

        }
        return rolePerVos;
    }

    @Override
    public List<RolePermissionVo> selectRoleName(String role_name) {
        List<RoleEntity> roles = roleMapper.selectRoleName(role_name);
        List<RolePermissionVo> rolePerVos = new ArrayList<>();
        if (roles.size()>0) {
            for (RoleEntity role : roles) {
                RolePermissionVo rolePermissionVo = new RolePermissionVo();
                rolePermissionVo.setRole_id(role.getRole_id());
                rolePermissionVo.setRole_name(role.getRole_name());
                Wrapper wrapper = new EntityWrapper();
                wrapper.eq("role_id", role.getRole_id());
                List<RolePermissionRefEntity> rolePermissionRefEntitys= rolePermissionMapper.selectList(wrapper);
                List list= new ArrayList();
                for (RolePermissionRefEntity rolePermissionRefEntity:rolePermissionRefEntitys)
                {
                    list.add(rolePermissionRefEntity.getPermission_id());
                }
                rolePermissionVo.setPerIds(list);
                rolePerVos.add(rolePermissionVo);
            }

        }
        return rolePerVos;
    }

    @Override
    public List<ModularVo> getRoleAdd() {
        List<ModularEntity> modularEntitieList=modularMapper.selectModularAll();
        List<ModularVo> modularVos= new ArrayList<>();
        for (ModularEntity modularEntity:modularEntitieList){
            ModularVo modularVo=new ModularVo();
            modularVo.setModular_id(modularEntity.getModular_id());
            modularVo.setModular_name(modularEntity.getModular_name());
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("modular_id",modularEntity.getModular_id());
            List<PermissionEntity> list = permissionMapper.selectList(wrapper);
            modularVo.setPers(list);
            modularVos.add(modularVo);
        }
        return modularVos;
    }

    @Override
    public int addRole(String role_name, Integer[] permissionId) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole_name(role_name);
        roleEntity.setCreate_time(new Date());
        roleEntity.setUpdate_time(new Date());
        Integer insert = roleMapper.insert(roleEntity);
        EntityWrapper<RoleEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("role_name",role_name);
        List<RoleEntity> roleEntitys = roleMapper.selectList(wrapper);
        RoleEntity role= roleEntitys.get(0);
        int role_id=role.getRole_id();
        RolePermissionRefEntity rolePermissionRefEntity = new RolePermissionRefEntity();
        rolePermissionRefEntity.setRole_id(role_id);
        for (int i=0;i<permissionId.length;i++){
            rolePermissionRefEntity.setPermission_id(permissionId[i]);
            Integer insert1 = rolePermissionMapper.insert(rolePermissionRefEntity);
            if(insert1==0){
                return 0;
            }
        }

        return insert;
    }
    @Override
    public List<RoleEntity> selectGetRoleName(String role_name) {
        EntityWrapper<RoleEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("role_name",role_name);
        List<RoleEntity> roleEntitys = roleMapper.selectList(wrapper);
        return roleEntitys;
    }

    @Override
    public int deleteRoles(Integer[] roleId) {
        Integer number=0;
        for(int i=0;i<roleId.length;i++){
            number = roleMapper.deleteById(roleId[i]);
            if (number>0) {
                Wrapper wrapper = new EntityWrapper();
                wrapper.eq("role_id",roleId[i]);
                number = rolePermissionMapper.delete(wrapper);
            }
        }
        return number;
    }

    @Override
    public Map<String, Object> toRoleEdit(Integer role_id) {
        RoleEntity role = roleMapper.selectById(role_id);
        Map<String,Object> map= new HashMap<>();
        map.put("role",role);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("role_id",role_id);
        List<RolePermissionRefEntity> list = rolePermissionMapper.selectList(wrapper);
        List per_ids=new ArrayList();
        for (RolePermissionRefEntity roleper:list){
            int permission_id = roleper.getPermission_id();
            per_ids.add(permission_id);
        }
        List permission=null;
        if(per_ids.size()>0){
             permission = permissionMapper.selectBatchIds(per_ids);

        }
        map.put("permission",permission);
        List<ModularVo> modularVo = getRoleAdd();
        map.put("modularVo",modularVo);
        return map;
    }

    @Override
    public int deleteRoleById(Integer role_id) {
       Integer number = roleMapper.deleteById(role_id);
        if (number>0) {
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("role_id",role_id);
            number = rolePermissionMapper.delete(wrapper);
        }
        return number;
    }

    @Override
    public List alterRoleName(String role_name, int role_id) {
        EntityWrapper<RoleEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("role_name",role_name);
        wrapper.notIn("role_id",role_id);
        List<RoleEntity> roleEntitys = roleMapper.selectList(wrapper);
        return roleEntitys;
    }

    @Override
    public int saveEditRole(String role_name, int role_id, Integer[] permissionId) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole_name(role_name);
        roleEntity.setUpdate_time(new Date());
        roleEntity.setRole_id(role_id);
        Integer insert = roleMapper.updateById(roleEntity);
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("role_id",role_id);
        Integer integer = rolePermissionMapper.delete(wrapper);
        RolePermissionRefEntity rolePermissionRefEntity= new RolePermissionRefEntity();
        rolePermissionRefEntity.setRole_id(role_id);
            for(int i=0;i<permissionId.length;i++){
               rolePermissionRefEntity.setPermission_id(permissionId[i]);
                Integer insert1 = rolePermissionMapper.insert(rolePermissionRefEntity);
                if(insert1==0){
                    return 0;
                }

        }

        return insert;
    }

    @Override
    public double selectRoleCount() {
        Wrapper wrapper = new EntityWrapper();
        Integer countnumber = roleMapper.selectCount(wrapper);
           double countpage=Math.ceil(countnumber/8.0);
        return countpage;
    }
}
