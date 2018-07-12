package com.seadun.rebot.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Utils {
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("x-real-ip");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据传入的类型获取spring管理的对应的bean
	 * 
	 * @param clazz
	 * @param request
	 * @return
	 */
	public static <T> T getSpringBean(Class<T> clazz, HttpServletRequest request) {
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return factory.getBean(clazz);
	}

	/**
	 * 得到几天前的凌晨时间
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateBefore(int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1 * before);
		Date date = calendar.getTime();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		return sdformat.format(date)+" 00:00:00";
	}
}
