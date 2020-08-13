package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.CustomException;
import com.guaitilsoft.models.User;
import com.guaitilsoft.repositories.UserRepository;
import com.guaitilsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    private AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImp(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User login(String email, String password) throws CustomException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return userRepository.findByEmail(email);
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            throw new CustomException("Usuario o contraseña incorrectos", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public User register(User user) throws CustomException{
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        } else {
            throw new CustomException("El usuario ya esta en uso", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public User search(String email) throws CustomException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("El usuario no fue encontrado", HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
