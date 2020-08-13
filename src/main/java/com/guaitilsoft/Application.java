package com.guaitilsoft;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@EntityScan(basePackages = {"com.guaitilsoft.models"})
public class Application implements CommandLineRunner {

	private MultimediaService multimediaService;
	private UserService userService;

	@Autowired
	public Application(MultimediaService multimediaService, UserService userService) {
		this.multimediaService = multimediaService;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Person person = new Person();
			person.setId("1");
			person.setName("Guatil");
			person.setEmail("guaitil_default_admin@gmail.com");
			person.setFirstLastName("Soft");
			person.setTelephone("8888888");
			person.setSecondLastName("Default Admin");
			User user = new User();
			user.setFirstLogin(false);
			user.setPassword("1234");
			user.setPerson(person);
			user.setRoles(new ArrayList<>(Collections.singletonList(Role.ROLE_ADMIN)));
			userService.register(user);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		multimediaService.deleteAll();
		multimediaService.init();

	}
}
