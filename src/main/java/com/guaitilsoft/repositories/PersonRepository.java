package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String>{
 }
