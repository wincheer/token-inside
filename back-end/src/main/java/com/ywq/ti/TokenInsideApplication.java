package com.ywq.ti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@MapperScan(basePackages = "com.ywq.ti.dao")
@SpringBootApplication(scanBasePackages = "com.ywq.ti")
public class TokenInsideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenInsideApplication.class, args);
	}
	
//	@Bean
//    public EtherumParser startupRunner(){
//        return new EtherumParser();
//    }
}
