package com.guaitilsoft.services;

import com.guaitilsoft.models.User;

import java.util.List;

public interface UserService {
    User get(Long id);
    User login(String email, String password);
    User register(User user);
    void deleteByEmail(String email);
    void delete(Long id);
    void deleteUserByMemberId(Long memberId);
    User search(String email);
    List<User> getAllUsers();
}
