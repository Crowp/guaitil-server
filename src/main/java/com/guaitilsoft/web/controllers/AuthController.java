package com.guaitilsoft.web.controllers;

import com.guaitilsoft.config.security.TokenProvider;
import com.guaitilsoft.models.User;
import com.guaitilsoft.services.UserService;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private ModelMapper modelMapper;
    private TokenProvider tokenProvider;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, TokenProvider tokenProvider) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login (@RequestParam String email,
                                               @RequestParam String password) {
        return ResponseEntity.ok().body(createToken(userService.login(email, password)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        return ResponseEntity.ok().body(createToken(userService.register(user)));
    }

    @DeleteMapping(value = "{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> delete(@PathVariable String email) {
        UserResponse userResponse = modelMapper.map(userService.search(email), UserResponse.class);
        userService.delete(email);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping(value = "{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> search(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.search(email));
    }

    private UserResponse createToken(User user){
        String token = this.tokenProvider.createToken(user.getEmail(), user.getRoles(), user.getMember());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(token);
        return userResponse;
    }

}
