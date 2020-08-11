package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;
import com.guaitilsoft.repositories.PersonRepository;
import com.guaitilsoft.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public Person get(Long id) {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(Long id, User entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
