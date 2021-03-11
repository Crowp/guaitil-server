package com.guaitilsoft.services.person;

import com.guaitilsoft.models.Person;

public interface PersonRepositoryService {

    Person get(String id);

    boolean existPerson(String personId);
}
