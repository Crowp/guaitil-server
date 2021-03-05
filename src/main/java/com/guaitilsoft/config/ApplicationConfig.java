package com.guaitilsoft.config;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.PersonType;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ApplicationConfig implements CommandLineRunner  {
    private final MultimediaService multimediaService;
    private final UserService userService;

    @Autowired
    public ApplicationConfig(MultimediaService multimediaService, UserService userService) {
        this.multimediaService = multimediaService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        try {
            Person person = new Person();
            person.setId("1");
            person.setName("Guatil");
            person.setEmail("guaitil_default_admin@gmail.com");
            person.setFirstLastName("Soft");
            person.setTelephone("8888888");
            person.setSecondLastName("Default Admin");
            person.setGender(Gender.MALE);
            person.setCreatedAt(LocalDateTime.now());
            person.setUpdatedAt(LocalDateTime.now());
            person.setPersonType(PersonType.ROLE_MEMBER);

            Member member = new Member();
            member.setOccupation("Admin");
            member.setPerson(person);
            member.setMemberType(MemberType.ASSOCIATED);
            member.setLocals(new ArrayList<>());

            User user = new User();
            user.setFirstLogin(false);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword("1234");
            user.setMember(member);
            List<Role> roles = new ArrayList<>(Collections.singletonList(Role.ROLE_ADMIN));
            roles.add(Role.ROLE_SUPER_ADMIN);
            user.setRoles(roles);
            userService.register(user);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        multimediaService.init();
    }
}
