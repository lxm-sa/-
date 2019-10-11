package com.yb.base.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yb.base.shiro.ShiroFormAuthenticationFilter;
import com.yb.base.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mayn on 2019/7/24.
 */
@SpringBootConfiguration
public class ShiroConfig {
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    @Bean(name="shiroFilter")
    public Object getShiroFilter(){

        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        //1.指定Shiro的SecurityManager对象
        factoryBean.setSecurityManager(this.getSecurityMAnager());
        Map<String,Filter> filterMap =new HashMap<String,Filter>();
        filterMap.put("authc",this.getFormAuthenticationFilter());
        factoryBean.setFilters(filterMap);
        //2.配置过滤链
        Map<String, String> filterChain=new LinkedHashMap<>();
        filterChain.put("/toLogin", "anon");
        filterChain.put("/static/**", "anon");
        filterChain.put("/register", "anon");
        filterChain.put("/user/accountName", "anon");
        filterChain.put("/user/register", "anon");
        filterChain.put("/**", "user");
        //设置登录路径
        factoryBean.setLoginUrl("/user/loginUser");
        factoryBean.setUnauthorizedUrl("/toLogin");
        //4.登录成功路径
        factoryBean.setSuccessUrl("/toIndex");
        factoryBean.setFilterChainDefinitionMap(filterChain);

        try {
            return  factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //会话管理
    @Bean
    public SecurityManager getSecurityMAnager(){
        DefaultWebSecurityManager dwsm=new DefaultWebSecurityManager();
        dwsm.setRealm(this.getShiroRealm());
        return dwsm;
    }
    @Bean
    public ShiroRealm getShiroRealm(){
        ShiroRealm shiroRealm=new ShiroRealm();
        HashedCredentialsMatcher credentialsMatcher =new HashedCredentialsMatcher();
        //设置是md5算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //加密三次
        credentialsMatcher.setHashIterations(1);
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return  shiroRealm;
    }
    @Bean
    public ShiroFormAuthenticationFilter getFormAuthenticationFilter() {
        ShiroFormAuthenticationFilter formAuthenticationFilter =new ShiroFormAuthenticationFilter();
        //指定用户名的参数名
        formAuthenticationFilter.setUsernameParam("user_account");
        //指定密码的参数名
        formAuthenticationFilter.setPasswordParam("user_pwd");
        return formAuthenticationFilter;
    }
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
