package com.guaitilsoft;

import com.guaitilsoft.localDate.LocalDateFormatter;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.MemberType;
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
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

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

	@PostConstruct
	void setUTCTimezone(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-6"));
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+6"));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {guaitil_default_admin@gmail.com
			Person person = new Person();
			person.setId("1");
			person.setName("Guatil");
			person.setEmail("");
			person.setFirstLastName("Soft");
			person.setTelephone("8888888");
			person.setSecondLastName("Default Admin");
			person.setGender(Gender.MALE);
			person.setCreatedAt(new Date());
			person.setUpdatedAt(new Date());

			Member member = new Member();
			member.setCreatedAt(new Date());
			member.setUpdatedAt(new Date());
			member.setOccupation("Admin");
			member.setPerson(person);
			member.setMemberType(MemberType.ASSOCIATED);
			member.setLocals(new ArrayList<>());

			User user = new User();
			user.setFirstLogin(false);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			user.setPassword("1234");
			user.setMember(member);
			List<Role> roles = new ArrayList<>(Collections.singletonList(Role.ROLE_ADMIN));
			roles.add(Role.ROLE_SUPER_ADMIN);
			user.setRoles(roles);
			userService.register(user);

		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		multimediaService.init();
	}

	@Bean
	@Primary
	public Formatter<LocalDate> localDateFormatter() {
		return new LocalDateFormatter();
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multiPartResolver(){
		return new CommonsMultipartResolver();
	}
}
