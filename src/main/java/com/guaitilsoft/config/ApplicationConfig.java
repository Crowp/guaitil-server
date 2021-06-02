package com.guaitilsoft.config;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.PersonType;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.multimedia.MultimediaService;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.utils.EmailNewAccountTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ApplicationConfig implements CommandLineRunner  {

    @Value("${user.password}")
    private String defaultPassword;

    @Value("${user.email}")
    private  String defaultEmail;

    private final MultimediaService multimediaService;
    private final UserRepositoryService userRepositoryService;

    @Autowired
    public ApplicationConfig(MultimediaService multimediaService,
                             @Qualifier("UserRepositoryServiceBasic") UserRepositoryService userRepositoryService) {
        this.multimediaService = multimediaService;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void run(String... args) {
        try {
            Person person = new Person();
            person.setId("1");
            person.setName("Guatil");
            person.setEmail(this.defaultEmail);
            person.setFirstLastName("Soft");
            person.setTelephone("88888888");
            person.setSecondLastName("Default Admin");
            person.setGender(Gender.MALE);
            person.setCreatedAt(LocalDateTime.now());
            person.setUpdatedAt(LocalDateTime.now());
            person.setPersonType(PersonType.ROLE_MEMBER);

            Member member = new Member();
            member.setOccupation("Administrator");
            member.setPerson(person);
            member.setMemberType(MemberType.ASSOCIATED);
            member.setLocals(new ArrayList<>());
            member.setAffiliationDate(LocalDateTime.now());

            User user = new User();
            user.setFirstLogin(false);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword(this.defaultPassword);
            user.setMember(member);
            List<Role> roles = new ArrayList<>(Collections.singletonList(Role.ROLE_ADMIN));
            roles.add(Role.ROLE_SUPER_ADMIN);
            user.setRoles(roles);
            userRepositoryService.register(user);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        multimediaService.init();
    }
}
