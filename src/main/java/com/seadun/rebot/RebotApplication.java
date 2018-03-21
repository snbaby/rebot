package com.seadun.rebot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.seadun.rebot.mapper")
public class RebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebotApplication.class, args);
	}
}
