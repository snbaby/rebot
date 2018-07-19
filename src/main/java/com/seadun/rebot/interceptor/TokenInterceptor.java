package com.seadun.rebot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.RebotException;

/**
 * 
 * @author Paul token 拦截器，当请求中未带token时，拦截请求，并验证token是否过期
 */
public class TokenInterceptor implements HandlerInterceptor {
	private final Logger _logger = LoggerFactory.getLogger(this.getClass()) ;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equals("OPTIONS")) {//解决跨域OPTIONS 问题
			return true;
		}else {//session简单检测
			// 获取session里的登录状态值
			String userStr = (String) request.getSession().getAttribute("isLogin");
			// 如果登录状态不为空则返回true，返回true则会执行相应controller的方法
			if (userStr != null) {
				return true;
			} else {
				_logger.info(">>>>>用户未登陆");
				throw new RebotException(RebotExceptionConstants.USER_NOT_LOGIN_EXCEPTION_CODE,
						RebotExceptionConstants.USER_NOT_LOGIN_EXCEPTION_MESSAGE,
						RebotExceptionConstants.USER_NOT_LOGIN_EXCEPTION_HTTP_STATUS);
			}
		}
	}
	
}
