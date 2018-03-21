package com.seadun.rebot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.seadun.rebot.interceptor.TokenInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport  {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/auth/token");
		super.addInterceptors(registry);
	}
}
