package com.yb.base.shiro;


import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by mayn on 2019/7/24.
 */
public abstract  class ShiroAuthorizingRealm extends AuthorizingRealm {
    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        //如果包含OR
        if(permission.contains(OR_OPERATOR)){
            //切割字符串
            String[] permissions = permission.split(OR_OPERATOR);
            //遍历数组
            for(String orPermission : permissions) {
                if(isPermittedWithNotOperator(principals, orPermission)) {
                    //只有有一个权限为true就返回true
                    return true;
                }
            }
            return false;
        } else if(permission.contains(AND_OPERATOR)) {
            //	//dictionary:to_edit and dictionary:delete
            String[] permissions = permission.split(AND_OPERATOR);
            for(String orPermission : permissions) {
                //判断是否两个权限都存在，否则返回false
                //只有有一个是false我们就返回false
                if(!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            //非真
            return isPermittedWithNotOperator(principals, permission);
        }
    }


    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        //判断权限字符串前缀是否有"not "关键字。
        if(permission.startsWith(NOT_OPERATOR)) {
            //如果有，就返回相反的结构
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }
}

