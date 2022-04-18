package com.akshaya.sf.spring.odata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SfSpringOdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SfSpringOdataApplication.class, args);
	}

}
