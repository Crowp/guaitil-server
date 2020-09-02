package com.guaitilsoft.services;

import com.guaitilsoft.models.User;

import java.util.List;

public interface UserService {
    User login(String email, String password);
    User register(User user);
    void delete(String email);
    User search(String email);
    List<User> getAllUsers();
}
