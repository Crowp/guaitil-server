package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.repositories.PersonRepository;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.services.ReservationService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    private PersonRepository personRepository;
    private ReservationService reservationService;
    private MemberService memberService;

    @Autowired
    public PersonServiceImp(PersonRepository personRepository, ReservationService reservationService, MemberService memberService) {
        this.personRepository = personRepository;
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @Override
    public List<Person> list() {
        Iterable<Person> iterable = personRepository.findAll();
        List<Person> person = new ArrayList<>();
        iterable.forEach(person::add);
        return person;
    }

    @Override
    public Person get(String id) {
        assert id != null;

        Person person = personRepository.findById(id).orElse(null);
        if(person != null){
            return person;
        }
        throw new EntityNotFoundException("No se encontro una persona con el id" + id);
    }

    @Override
    public void save(Person entity) {
        assert entity != null;

        if(personRepository.existPersonId(entity.getId())){
            throw new ApiRequestException("Cedula: " + entity.getId() + " esta ocupada");
        }
        if(personRepository.existEmail(entity.getEmail())){
            throw new ApiRequestException("Email: " + entity.getEmail() + " esta ocupado");
        }
        personRepository.save(entity);

    }

    @Override
    public void update(String id, Person entity) {
        assert id != null;
        assert entity != null;
        Instant nowGmt = Instant.now();
        DateTimeZone americaCostaRica = DateTimeZone.forID("America/Costa_Rica");
        DateTime nowCostaRica = nowGmt.toDateTime(americaCostaRica);
        Date today = nowCostaRica.toDate();


        Person person = this.get(id);
        person.setName(entity.getName());
        person.setFirstLastName(entity.getFirstLastName());
        person.setSecondLastName(entity.getSecondLastName());
        person.setTelephone(entity.getTelephone());
        person.setGender(entity.getGender());
        person.setEmail(entity.getEmail());
        person.setPersonType(entity.getPersonType());
        entity.setUpdatedAt(today);

        personRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        assert id != null;

        Person person = this.get(id);

        memberService.deleteMemberByPersonId(id);
        reservationService.deleteReservationByPersonId(id);
        personRepository.delete(person);
    }
}
