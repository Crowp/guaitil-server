package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String>{

}
