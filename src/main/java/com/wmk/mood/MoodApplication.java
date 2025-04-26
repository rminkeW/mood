package com.wmk.mood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wmk.mood.mapper")
public class MoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodApplication.class, args);
	}

}
