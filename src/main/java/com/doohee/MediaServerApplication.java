package com.doohee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.doohee.main", "com.doohee.common", "com.doohee"})
public class MediaServerApplication {
//	@Autowired
//	GreetingController greetingController;
	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}

}
