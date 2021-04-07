package com.guaitilsoft.services.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.user.UserLazyResponse;
import com.guaitilsoft.web.models.user.UserReportResponse;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse get(Long id);
    List<UserLazyResponse> getUsersAdmins();
    UserResponse getByMemberID(Long id);
    UserResponse login(String email, String password);
    UserResponse register(UserRequest user);
    UserResponse updateRoles(List<Role> roles, Long id);
    void deleteByEmail(String email);
    UserResponse resetPassword(Long id, String newPassword);
    List<UserLazyResponse> getAllUsers();
    void delete(Long id);
    List<UserReportResponse> getUsersReport();
}
