package com.yb.base.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by mayn on 2019/7/25.
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 成功以后加入会话信息
     */

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //将Shiro的会话信息加入到HttpSession里面
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpSession session = request1.getSession();
        //Map<String, Object> principal = (Map<String, Object>) subject.getPrincipal();
        session.setAttribute("user",subject.getPrincipal());

        return super.onLoginSuccess(token, subject, request, response);
    }

}
