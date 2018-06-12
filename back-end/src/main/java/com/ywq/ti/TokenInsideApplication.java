package com.ywq.ti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@MapperScan(basePackages = "com.ywq.ti.dao")
@SpringBootApplication(scanBasePackages = "com.ywq.ti")
@EnableScheduling
public class TokenInsideApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TokenInsideApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TokenInsideApplication.class, args);
	}
}
