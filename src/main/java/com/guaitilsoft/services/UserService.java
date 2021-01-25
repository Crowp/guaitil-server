package com.guaitilsoft.services;

import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;

import java.util.List;

public interface UserService {
    User get(Long id);
    User getByMemberID(Long id);
    User login(String email, String password);
    User register(User user);
    User updateRoles(List<Role> roles, Long id);
    void deleteByEmail(String email);
    void delete(Long id);
    void deleteUserByMemberId(Long memberId);
    User search(String email);
    User resetPassword(Long id, String newPassword);
    List<User> getAllUsers();
}
