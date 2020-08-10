package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.repositories.PersonRepository;
import com.guaitilsoft.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceImp implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Override
    public List<Person> list() {
        Iterable<Person>iterable =personRepository.findAll();
        List<Person>person =new ArrayList<>();
        iterable.forEach(person::add);
        return person;
    }

    @Override
    public Person get(String id) {
        return null;
    }

    @Override
    public void save(Person entity) {
        personRepository.save(entity);

    }

    @Override
    public void update(String id, Person entity) {
        Person person = this.get(id);
        person.setName(entity.getName());
        person.setFirstLastName(entity.getFirstLastName());
        person.setSecondLastName(entity.getSecondLastName());
        person.setTelephone(entity.getTelephone());
        person.setPersonType(entity.getPersonType());
        personRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        Person person = this.get(id);
        if(person != null){
            personRepository.delete(person);
        }

    }
}
