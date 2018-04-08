package com.conan.crawler.server.pre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.conan.crawler.server.pre.mapper")
public class ConanCrawlerServerPreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConanCrawlerServerPreApplication.class, args);
	}
}
