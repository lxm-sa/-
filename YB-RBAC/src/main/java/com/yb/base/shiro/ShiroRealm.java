package com.yb.base.shiro;

import com.yb.base.pojo.PermissionEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.loginmodule.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/7/24.
 */
public class ShiroRealm extends ShiroAuthorizingRealm {
    //只要对象是放在Spring容器里面的，都可以使用@Autowired
    @Autowired
    private UserService userService;
    /**
     * 用于权限授予的方法
     * 基于WEB实现，必须要请求有授权的行为，才会调用授权方法
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Map<String, Object> user = (Map<String, Object>) principalCollection.getPrimaryPrincipal();

        List<PermissionEntity> permissions = (List<PermissionEntity>) user.get("permission");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        for (PermissionEntity permission : permissions) {
            info.addStringPermission((String)permission.getPermission_key());
        }
        return info;
    }
    //用于权限校验的方法

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Map<String, Object> user = userService.authcUser(authenticationToken.getPrincipal());
        if (user.size()!=0){
            //盐值
            UserEntity user1 = (UserEntity) user.get("user");
            ByteSource salt = ByteSource.Util.bytes("abcd123" + user1.getUser_salt());
            return new SimpleAuthenticationInfo(user,user1.getPassword(),salt,this.getName());
        }
        //验证不通过
        return null;
    }

}
