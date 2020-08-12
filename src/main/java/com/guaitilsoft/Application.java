package com.guaitilsoft;

import com.guaitilsoft.services.MultimediaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Resource
	MultimediaService multimediaService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		multimediaService.deleteAll();
		multimediaService.init();
	}
}
