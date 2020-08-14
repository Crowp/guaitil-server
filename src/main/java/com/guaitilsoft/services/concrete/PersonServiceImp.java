package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.repositories.PersonRepository;
import com.guaitilsoft.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImp(PersonRepository personRepository) {
        this.personRepository = personRepository;
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

        personRepository.save(entity);
    }

    @Override
    public void update(String id, Person entity) {
        assert id != null;
        assert entity != null;

        Person person = this.get(id);
        person.setName(entity.getName());
        person.setFirstLastName(entity.getFirstLastName());
        person.setSecondLastName(entity.getSecondLastName());
        person.setTelephone(entity.getTelephone());
        person.setGender(entity.getGender());
        person.setEmail(entity.getEmail());
        person.setPersonType(entity.getPersonType());

        personRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        assert id != null;

        Person person = this.get(id);
        personRepository.delete(person);
    }
}
