package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PersonRepositoryTests {

    @Autowired
    PersonRepository personRepository;

    Person basicPerson;

    @Before
    public void init() {
        this.basicPerson = createBasicPerson();
    }

    @Test
    public void should_create_person() {
        Person personStored = personRepository.save(basicPerson);
        assertThat(personStored.getId()).isEqualTo(basicPerson.getId());
    }

    @Test
    public void should_update_person() {
        Person personStored = personRepository.save(basicPerson);

        personStored.setName("Alejandro");

        Person personUpdated = personRepository.save(personStored);

        assertThat(personUpdated.getName()).isEqualTo("Alejandro");
    }

    @Test
    public void should_delete_person() {
        Person personStored = personRepository.save(basicPerson);

        personRepository.delete(personStored);

        Person existsPerson = personRepository.findById("901110534").orElse(null);

        assertThat(existsPerson).isNull();
    }

    private Person createBasicPerson() {
        Person person = new Person();
        person.setId("901110534");
        person.setName("Ricardo");
        person.setFirstLastName("Morataya");
        person.setSecondLastName("Sandoval");
        person.setGender(Gender.MALE);
        person.setTelephone("85807271");
        person.setEmail("ricardojms1999@gmail.com");
        person.setPersonType(PersonType.ROLE_MEMBER);
        return person;
    }
}
