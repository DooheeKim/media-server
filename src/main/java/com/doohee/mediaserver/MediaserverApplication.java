package com.doohee.mediaserver;

import com.doohee.restservice.GreetingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.doohee"})
public class MediaserverApplication {
//	@Autowired
//	GreetingController greetingController;
	public static void main(String[] args) {
		SpringApplication.run(MediaserverApplication.class, args);

	}

}
