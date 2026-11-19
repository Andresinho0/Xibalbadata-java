package com.iitca.tecnodesarrollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class XibalbaDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(XibalbaDataApplication.class, args);
	}

}
