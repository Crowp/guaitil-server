package com.guaitilsoft.services.person;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PersonRepositoryServiceImp implements PersonRepositoryService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryServiceImp(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person get(String id) {
        assert id != null;

        Person person = personRepository.findById(id).orElse(null);
        if(person != null){
            return person;
        }
        throw new EntityNotFoundException("No se encontró una persona con el id" + id);
    }

    @Override
    public boolean existPerson(String personId) {
        return this.personRepository.existsById(personId);
    }
}
