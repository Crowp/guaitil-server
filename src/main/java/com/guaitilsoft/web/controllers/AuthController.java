package com.guaitilsoft.web.controllers;

import com.guaitilsoft.config.security.TokenProvider;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.UserService;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.user.UserLazyResponse;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, TokenProvider tokenProvider, MemberService memberService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserLazyResponse>> get(){
        Type listType  = new TypeToken<List<UserLazyResponse>>(){}.getType();
        List<UserLazyResponse> users = modelMapper.map(userService.getAllUsers(),listType);
        return  ResponseEntity.ok().body(users);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        UserResponse user = modelMapper.map(userService.get(id), UserResponse.class);
        return  ResponseEntity.ok().body(user);
    }

    @GetMapping("/member/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getByMemberId(@PathVariable Long id){
        UserResponse user = modelMapper.map(userService.getByMemberID(id), UserResponse.class);
        return  ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestParam String email,
                                               @RequestParam String password) {
        return ResponseEntity.ok().body(createToken(userService.login(email, password)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.setMember(memberService.get(user.getMember().getId()));
        return ResponseEntity.ok().body(createToken(userService.register(user)));
    }

    @PutMapping("/reset")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> reset(@RequestParam Long id,
                                              @RequestParam String newPassword) {
        User user = userService.resetPassword(id, newPassword);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/update-roles/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserResponse> updateRoles(@RequestBody List<Role> roles, @PathVariable Long id) {
        User user = userService.updateRoles(roles, id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> delete(@PathVariable String email) {
        UserResponse userResponse = modelMapper.map(userService.search(email), UserResponse.class);
        userService.deleteByEmail(email);
        return ResponseEntity.ok().body(userResponse);
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
