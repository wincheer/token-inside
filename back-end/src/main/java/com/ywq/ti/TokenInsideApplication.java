package com.ywq.ti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TokenInsideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenInsideApplication.class, args);
	}
	
	@Bean
    public EtherumParser startupRunner(){
        return new EtherumParser();
    }
}
