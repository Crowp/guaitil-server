package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String>{

 }
