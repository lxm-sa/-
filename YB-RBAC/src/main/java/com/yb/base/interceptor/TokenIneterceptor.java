package com.yb.base.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yb.base.annotation.TokenForm;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class TokenIneterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获得执行方法的TokenForm注解
		HandlerMethod hm= (HandlerMethod) handler;
		HttpSession session = request.getSession();
		TokenForm tokenForm = hm.getMethodAnnotation(TokenForm.class);
		if (tokenForm!=null) {
			//判断如果create=true，创建Token
			if (tokenForm.create()==true) {
				//创建一个Token
				String token=UUID.randomUUID().toString();
				
				session.setAttribute("token", token);
			}
			if (tokenForm.remove()==true) {
				//判断表单的token和session的token是否一致
				String formToken = request.getParameter("formToken");
				Object token = session.getAttribute("token");
				if (formToken.equals(token)) {
					session.removeAttribute("token");
					return true;
				}else {
					//从表单获得跳转的路径
					String invoke = request.getParameter("token.invoke");
					response.sendRedirect(invoke);
					return false;
				}
			}
			
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
