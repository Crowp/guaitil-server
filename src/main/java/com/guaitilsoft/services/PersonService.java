package com.guaitilsoft.services;

import com.guaitilsoft.models.Person;

import java.util.List;

public interface PersonService {

    List<Person> list();

    Person get(String id);

    void save(Person entity);

    void update(String id, Person entity);

    void delete(String id);
}
