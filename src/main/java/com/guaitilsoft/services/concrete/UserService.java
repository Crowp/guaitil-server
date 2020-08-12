package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.authoriry.User;

import java.util.List;

public interface UserService {
    List<User> list();

    Person get(Long id);

    void save(User entity);

    void update(Long id,User entity);

    void delete(Long id);
}
