package com.seadun.rebot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author sn_baby
 * 判断当前的系统中是否有可分配的资产号
 *
 */
@Slf4j
@Service
public class SboxInterceptor implements HandlerInterceptor {
	
	@Override
	public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
}
