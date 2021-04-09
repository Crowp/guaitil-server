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
    private final EmailSenderService emailSenderService;

    @Autowired
    public UserServiceImp(UserRepositoryService userRepositoryService,
                          ModelMapper modelMapper,
                          TokenProvider tokenProvider,
                          EmailSenderService emailSenderService) {
        this.userRepositoryService = userRepositoryService;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
        this.emailSenderService = emailSenderService;
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

    private List<UserReportResponse> getUserResponseReport(List<User> users){
        List<UserReportResponse> userReportResponses = this.parseToUserReportResponse(getUsersAdmins(users));
        userReportResponses.forEach(ur -> ur.setRole(Role.ROLE_ADMIN.getMessage()));
        return userReportResponses;
    }

    private List<User> getUsersAdmins(List<User> users){
        return users.stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_ADMIN))
                .collect(Collectors.toList());
    }

    private List<UserReportResponse> parseToUserReportResponse(List<User> list){
        Type listType  = new TypeToken<List<UserReportResponse>>(){}.getType();
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
        UserResponse userResponse = createToken(userRepositoryService.register(this.parseToUser(user)));

        this.sendEmailToNewUser(userResponse, user.getPassword());

        return userResponse;
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

    private void sendEmailToNewUser(UserResponse userResponse, String password) {
        String name = userResponse.getMember().getPerson().getName();
        String lastname = userResponse.getMember().getPerson().getFirstLastName();
        String secondLastname =userResponse.getMember().getPerson().getSecondLastName();
        String email =userResponse.getMember().getPerson().getEmail();
        String template = new EmailNewAccountTemplate()
                .addEmail(email)
                .addFullName(name + " " + lastname + " " + secondLastname)
                .addGenericPassword(password)
                .getTemplate();

        emailSenderService.sendEmail("Envio de datos de la nueva cuenta en Guaitil Tour", "guaitiltour.cr@gmail.com", email, template);
    }
}
