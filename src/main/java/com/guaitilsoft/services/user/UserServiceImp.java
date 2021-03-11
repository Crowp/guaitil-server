package com.guaitilsoft.services.user;

import com.guaitilsoft.config.security.TokenProvider;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.user.UserLazyResponse;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

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
    public UserResponse get(Long id) {
        return null;
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
    public void deleteByEmail(String email) {
        userRepositoryService.deleteByEmail(email);
    }

    @Override
    public UserResponse search(String email) {
        return this.parseToUserResponse(userRepositoryService.search(email));
    }

    @Override
    public UserResponse resetPassword(Long id, String newPassword) {
        return this.parseToUserResponse(userRepositoryService.resetPassword(id, newPassword));
    }

    private List<UserLazyResponse> parseToUserLazyResponseList (List<User> users){
        Type listType  = new TypeToken<List<UserLazyResponse>>(){}.getType();
        return modelMapper.map(users, listType);
    }

    private UserResponse parseToUserResponse(User user){
        return modelMapper.map(user, UserResponse.class);
    }

    private User parseToUser (UserRequest userRequest){
        return modelMapper.map(userRequest, User.class);
    }

    private UserResponse createToken(User user){
        String token = this.tokenProvider.createToken(
                user.getEmail(),
                user.getRoles(),
                this.modelMapper.map(user.getMember(), MemberRequest.class));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(token);
        return userResponse;
    }
}
