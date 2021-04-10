package com.guaitilsoft.services.user;

import com.guaitilsoft.config.security.TokenProvider;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.utils.EmailNewAccountTemplate;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.user.UserLazyResponse;
import com.guaitilsoft.web.models.user.UserReportResponse;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepositoryService userRepositoryService;
    private final ModelMapper modelMapper;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserServiceImp(UserRepositoryService userRepositoryService,
                          ModelMapper modelMapper,
                          TokenProvider tokenProvider) {
        this.userRepositoryService = userRepositoryService;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public List<UserLazyResponse> getAllUsers() {
        return this.parseToUserLazyResponseList(userRepositoryService.getAllUsers());
    }

    @Override
    public void delete(Long id) {
        this.userRepositoryService.delete(id);
    }

    @Override
    public List<UserReportResponse> getUsersReport() {
        return getUserResponseReport(userRepositoryService.getAllUsers().stream().filter(u -> u.getId() != 1).collect(Collectors.toList()));
    }

    private List<UserReportResponse> getUserResponseReport(List<User> users) {
        List<UserReportResponse> userReportResponses = this.parseToUserReportResponse(getUsersAdmins(users));
        userReportResponses.forEach(ur -> ur.setRole(Role.ROLE_ADMIN.getMessage()));
        return userReportResponses;
    }

    private List<User> getUsersAdmins(List<User> users) {
        return users.stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_ADMIN))
                .collect(Collectors.toList());
    }

    private List<UserReportResponse> parseToUserReportResponse(List<User> list) {
        Type listType = new TypeToken<List<UserReportResponse>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public UserResponse get(Long id) {
        User user = this.userRepositoryService.get(id);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserLazyResponse> getUsersAdmins() {
        return this.parseToUserLazyResponseList(userRepositoryService.getAllUsers()
                .stream()
                .filter(u -> u.getRoles().contains(Role.ROLE_ADMIN) && u.getId() != 1)
                .collect(Collectors.toList()));
    }

    @Override
    public UserResponse getByMemberID(Long id) {
        return this.parseToUserResponse(userRepositoryService.get(id));
    }

    @Override
    public UserResponse login(String email, String password) {
        return createToken(userRepositoryService.login(email, password));
    }

    @Override
    public UserResponse register(UserRequest user) {
        return createToken(userRepositoryService.register(this.parseToUser(user)));
    }

    @Override
    public UserResponse updateRoles(List<Role> roles, Long id) {
        return this.parseToUserResponse(userRepositoryService.updateRoles(roles, id));
    }

    @Override
    public UserResponse resetPassword(Long id, String newPassword, Boolean sendEmail) {
        return this.parseToUserResponse(userRepositoryService.resetPassword(id, newPassword, sendEmail));
    }

    private List<UserLazyResponse> parseToUserLazyResponseList(List<User> users) {
        Type listType = new TypeToken<List<UserLazyResponse>>() {
        }.getType();
        return modelMapper.map(users, listType);
    }

    private UserResponse parseToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private User parseToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    private UserResponse createToken(User user) {
        String token = this.tokenProvider.createToken(user,
                this.modelMapper.map(user.getMember(), MemberRequest.class));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(token);
        return userResponse;
    }
}
