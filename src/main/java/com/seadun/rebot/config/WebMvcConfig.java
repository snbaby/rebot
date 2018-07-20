package com.seadun.rebot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.seadun.rebot.interceptor.CorsInterceptor;
import com.seadun.rebot.interceptor.TokenInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
		List<String> patterns = new ArrayList<>();
		patterns.add("/api/auth/login");
		patterns.add("/api/auth/logout");
		patterns.add("/api/init/memory-type");
		patterns.add("/api/init/user");
		patterns.add("/api/file");
		patterns.add("/api/sbox/data/**");
		patterns.add("/api/auth/client/token");
		patterns.add("/api/upgrade/**");
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
		super.addInterceptors(registry);
	}
}
