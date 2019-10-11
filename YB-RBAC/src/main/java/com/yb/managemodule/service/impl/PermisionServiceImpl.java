package com.yb.managemodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.base.mapper.ModularMapper;
import com.yb.base.mapper.PermissionMapper;
import com.yb.base.mapper.RolePermissionMapper;
import com.yb.base.pojo.ModularEntity;
import com.yb.base.pojo.PermissionEntity;
import com.yb.base.vo.PermissionVo;
import com.yb.managemodule.service.PermissionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mayn on 2019/8/24.
 */
@Service
public class PermisionServiceImpl implements PermissionService{
   @Autowired
    private PermissionMapper permissionMapper;
   @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private ModularMapper modularMapper;
    @Override
    public List<PermissionVo> selectPerList(int index) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.groupBy("permission_id");
        RowBounds rowBounds= new RowBounds((index-1)*8,8);
        List<Map<String, Object>> maps = permissionMapper.selectMapsPage(rowBounds,wrapper);
        List<PermissionVo> permissionVos=new ArrayList<>();
        if(maps.size()>0){

           for (Map<String, Object> per:maps){
               PermissionVo vo = new PermissionVo();
               vo.setModular_id((Long) per.get("modular_id"));
               vo.setPermission_id((Integer) per.get("permission_id"));
               vo.setPermission_name((String) per.get("permission_name"));
               vo.setPermission_action((String)per.get("permission_action"));
               vo.setPermission_parent((Long)per.get("permission_parent"));
               vo.setPermission_key((String)per.get("permission_key"));
               ModularEntity modularEntity = modularMapper.selectById((Long) per.get("modular_id"));
               vo.setModular_name(modularEntity.getModular_name());
               permissionVos.add(vo);
           }
        }
        return permissionVos;
    }

    @Override
    public double selectPerCount() {
        int count = permissionMapper.selectCount(new EntityWrapper<>());
        double count1=Math.ceil(count/8.0);
        return count1;
    }

    @Override
    public List<ModularEntity> selectModular() {
        List<ModularEntity> modularList = modularMapper.selectModularAll();
        return modularList;
    }

    @Override
    public List<PermissionEntity> selectPermission_parent() {
        List<PermissionEntity> permissionEntityList = permissionMapper.selectPermission_parent();
        return permissionEntityList;
    }

    @Override
    public int addPermission(PermissionEntity permissionEntity) {
        permissionEntity.setUpdate_time(new Date());
        permissionEntity.setCreate_time(new Date());
        Integer number = permissionMapper.insert(permissionEntity);
        return number;
    }

    @Override
    public int deleteCheckPermissions(Integer[] permissionId) {
        Integer number=0;
        for(int i=0;i<permissionId.length;i++){
            number = permissionMapper.deleteById(permissionId[i]);
            if (number>0) {
                Wrapper wrapper = new EntityWrapper();
                wrapper.eq("permission_id",permissionId[i]);
                 rolePermissionMapper.delete(wrapper);
            }
        }
        return number;
    }

    @Override
    public List<PermissionVo> selectPermission_name(String permission_name) {
        List<PermissionEntity> perList=permissionMapper.selectPermission_name(permission_name);
        List<PermissionVo> permissionVos=new ArrayList<>();
        if(perList.size()>0){
            for (PermissionEntity per:perList){
                PermissionVo vo = new PermissionVo();
                vo.setModular_id((Long) per.getModular_id());
                vo.setPermission_id((Integer) per.getPermission_id());
                vo.setPermission_name((String) per.getPermission_name());
                vo.setPermission_action((String)per.getPermission_action());
                vo.setPermission_parent((Long)per.getPermission_parent());
                vo.setPermission_key((String)per.getPermission_key());
                ModularEntity modularEntity = modularMapper.selectById((Long) per.getModular_id());
                vo.setModular_name(modularEntity.getModular_name());
                permissionVos.add(vo);
            }
        }
        return permissionVos;
    }

    @Override
    public Map<String, Object> toPermissionEdit(Integer permission_id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("permission_id",permission_id);
        PermissionEntity permissionEntity = permissionMapper.selectById(permission_id);
        Map<String ,Object> map = new HashMap<>();
        map.put("permission",permissionEntity);
        List<PermissionEntity> permissionEntityList = permissionMapper.selectPermission_parent();
        map.put("permissionEntityList",permissionEntityList);
        List<ModularEntity> modularList = modularMapper.selectModularAll();
        map.put("modularList",modularList);
        return map;
    }

    @Override
    public int deletePermissionById(Integer permission_id) {
        Integer number = permissionMapper.deleteById(permission_id);
        if (number>0) {
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("permission_id",permission_id);
            rolePermissionMapper.delete(wrapper);
        }
        return number;
    }

    @Override
    public int savePermission(PermissionEntity permissionEntity) {
        permissionEntity.setUpdate_time(new Date());
        Integer integer = permissionMapper.updateById(permissionEntity);
        return integer;
    }
}
