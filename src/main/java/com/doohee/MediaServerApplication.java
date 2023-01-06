package com.doohee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"com.doohee"})
@EnableAsync
public class MediaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}

}
