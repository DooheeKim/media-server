package com.doohee.main;

import com.doohee.main.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MediaServerApplicationTests {

	@Autowired
	Environment environment;

	@Test
	void environmentLoaded(){
		assertThat(environment.getProperty("server.main"))
				.isEqualTo("http://localhost:8080");
	}
}
