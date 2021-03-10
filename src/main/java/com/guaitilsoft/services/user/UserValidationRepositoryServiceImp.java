package com.guaitilsoft.services.user;

import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.member.MemberRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("UserRepositoryServiceValidation")
public class UserValidationRepositoryServiceImp implements UserRepositoryService {

    private final UserRepositoryService userRepositoryService;
    private final MemberRepositoryService memberRepositoryService;

    @Autowired
    public UserValidationRepositoryServiceImp(@Qualifier("UserRepositoryServiceBasic") UserRepositoryService userRepositoryService,
                                              @Qualifier("MemberRepositoryServiceBasic") MemberRepositoryService memberRepositoryService) {
        this.userRepositoryService = userRepositoryService;
        this.memberRepositoryService = memberRepositoryService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryService.getAllUsers();
    }

    @Override
    public User get(Long id) {
        User product = userRepositoryService.get(id);
        if(product != null){
            return product;
        }
        throw new EntityNotFoundException("No se encontró un usuario con el id: " + id);
    }

    @Override
    public User getByMemberID(Long id) {
        return userRepositoryService.getByMemberID(id);
    }

    @Override
    public User login(String email, String password) {
        return userRepositoryService.login(email, password);
    }

    @Override
    public User register(User user) {
        user.setMember(memberRepositoryService.get(user.getMember().getId()));
        return userRepositoryService.register(user);
    }

    @Override
    public User updateRoles(List<Role> roles, Long id) {
        return userRepositoryService.updateRoles(roles, id);
    }

    @Override
    public void deleteByEmail(String email) {
        userRepositoryService.deleteByEmail(email);
    }

    @Override
    public void delete(Long id) {
        userRepositoryService.delete(id);
    }

    @Override
    public void deleteUserByMemberId(Long memberId) {
        userRepositoryService.deleteUserByMemberId(memberId);
    }

    @Override
    public User search(String email) {
        User user = userRepositoryService.search(email);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("El usuario no fue encontrado");
    }

    @Override
    public User resetPassword(Long id, String newPassword) {
        return userRepositoryService.resetPassword(id, newPassword);
    }

    @Override
    public List<User> getUsersAdmin() {
        return userRepositoryService.getUsersAdmin();
    }
}
