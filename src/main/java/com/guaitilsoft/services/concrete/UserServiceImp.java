package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.User;
import com.guaitilsoft.repositories.UserRepository;
import com.guaitilsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    public List<User> getAllUsers() {
        Iterable<User> iterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        iterable.forEach(users::add);
        return users;
    }

    @Override
    public User login(String email, String password) throws ApiRequestException {
        assert email != null;
        assert password != null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return userRepository.findByEmail(email);
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            throw new ApiRequestException("Usuario o contraseña incorrectos");
        }
    }

    @Override
    public User register(User user) throws ApiRequestException{
        assert user != null;

        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        } else {
            throw new ApiRequestException("El usuario con el email: " + user.getEmail() + " ya existe");
        }
    }

    @Override
    public void delete(String email) {
        assert email != null;

        userRepository.deleteByEmail(email);
    }

    @Override
    public User search(String email) {
        assert email != null;

        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("El usuario no fue encontrado");
    }
}
