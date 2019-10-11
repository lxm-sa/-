package com.yb.base.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

/**
 * Created by mayn on 2019/8/6.
 */
public class Md5Utils {
    public static  String md5(String source,String userSalt ){
        String algorithmName="md5";
        Object salt="abcd123"+userSalt;
        int hashIterations=1;
        SimpleHash simpleHash=new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash.toString();
    }
    public static void main(String[] args) {
        String userSalt= UUID.randomUUID().toString();
        System.out.println(userSalt);
        System.out.println(Md5Utils.md5("123456",userSalt));
    }
}
