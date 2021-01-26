package com.guaitilsoft;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.PersonType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;


import java.util.ArrayList;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void testAppMember() {
		Member member = new Member();
		Person person = new Person();
		Local local = new Local();

		person.setId("50492829");
		person.setName("Tatiana");
		person.setFirstLastName("Lopez");
		person.setSecondLastName("juarez");
		person.setTelephone("80256989");
		person.setGender(Gender.FEMALE);
		person.setEmail("juanita@gmail.com");
		person.setCreatedAt(new Date());
		person.setPersonType(PersonType.ROLE_MEMBER);
		person.setUpdatedAt(new Date());

		member.setOccupation("Doctora");
		member.setCreatedAt(new Date());
		member.setUpdatedAt(new Date());
		member.setPerson(person);
		member.setLocals(new ArrayList<>());
		member.setMemberType(MemberType.ASSOCIATED);
		ResponseEntity<Member> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/member", member, Member.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}


}
