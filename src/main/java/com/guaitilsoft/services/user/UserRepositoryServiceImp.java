package com.guaitilsoft.services.user;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("UserRepositoryServiceBasic")
public class UserRepositoryServiceImp implements UserRepositoryService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserRepositoryServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
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
    public List<User> getUsersAdmin() {
        return this.getAllUsers()
                .stream()
                .filter(u -> u.getRoles().contains(Role.ROLE_ADMIN) || u.getRoles().contains(Role.ROLE_SUPER_ADMIN))
                .collect(Collectors.toList());
    }

    @Override
    public User get(Long id) {
        assert id != null;
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByMemberID(Long id) {
        assert id != null;
        Optional<User> optionalUser = userRepository.selectUserByMemberId(id);
        if(optionalUser.isPresent()){
            return  optionalUser.get();
        }
        throw new EntityNotFoundException("No se encontró un usuario que tenga un miembro con el id: " + id);
    }

    @Override
    public User login(String email, String password) {
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
    public User register(User user) {
        assert user != null;

        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setFirstLogin(true);
            userRepository.save(user);
            return user;
        } else {
            throw new ApiRequestException("El usuario con el email: " + user.getEmail() + " ya existe");
        }
    }

    @Override
    public User updateRoles(List<Role> roles, Long id) {
        assert id != null;
        User user = this.get(id);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteByEmail(String email) {
        assert email != null;
        userRepository.deleteByEmail(email);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        User user = this.get(id);

        userRepository.delete(user);
    }

    @Override
    public void deleteUserByMemberId(Long memberId) {
        Optional<User> optionalUser = userRepository.selectUserByMemberId(memberId);
        optionalUser.ifPresent(user -> this.delete(user.getId()));
    }

    @Override
    public User search(String email) {
        assert email != null;
        return userRepository.findByEmail(email);
    }

    @Override
    public User resetPassword(Long id, String newPassword) {
        User user = this.get(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return user;
    }
}
