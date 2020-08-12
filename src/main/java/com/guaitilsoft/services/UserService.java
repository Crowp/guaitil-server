package com.guaitilsoft.services;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;

import java.util.List;

public interface UserService {
    List<User> list();

    User get(Long id);

    void save(User entity);

    void update(Long id,User entity);

    void delete(Long id);
}
