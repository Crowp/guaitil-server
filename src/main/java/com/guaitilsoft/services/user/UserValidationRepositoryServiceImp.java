package com.guaitilsoft.services.user;

import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.models.constant.TypeEmail;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.utils.EmailNewAccountTemplate;
import com.guaitilsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("UserRepositoryServiceValidation")
public class UserValidationRepositoryServiceImp implements UserRepositoryService {

    private final UserRepositoryService userRepositoryService;
    private final MemberRepositoryService memberRepositoryService;
    private final EmailSenderService emailSenderService;

    @Value("${guaitil-domain.client}")
    private String urlGuaitil;

    @Value("${user.gmail-sender-email}")
    private String emailForm;

    @Autowired
    public UserValidationRepositoryServiceImp(UserRepositoryService userRepositoryService,
                                              MemberRepositoryService memberRepositoryService,
                                              EmailSenderService emailSenderService) {
        this.userRepositoryService = userRepositoryService;
        this.memberRepositoryService = memberRepositoryService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryService.getAllUsers();
    }

    @Override
    public User get(Long id) {
        User user = userRepositoryService.get(id);
        if(user != null){
            return user;
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
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            this.sendEmailToNewUser(user, user.getPassword(), TypeEmail.NEW_ACCOUNT_ADMIN);
        }else {
            this.sendEmailToNewUser(user, user.getPassword(), TypeEmail.NEW_ACCOUNT_MEMBER);
        }
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
    public User resetPassword(Long id, String newPassword, Boolean sendEmail) {
        User user = this.get(id);
        if(sendEmail){
            this.sendEmailToNewUser(user, newPassword, TypeEmail.RESET_PASSWORD);
        }
        return userRepositoryService.resetPassword(id, newPassword, sendEmail);
    }

    @Override
    public List<User> getUsersAdmin() {
        return userRepositoryService.getUsersAdmin();
    }

    private void sendEmailToNewUser(User user, String password, TypeEmail typeEmail) {
        String email = user.getMember().getPerson().getEmail();
        String template = new EmailNewAccountTemplate()
                .addEmail(email)
                .addFullName(Utils.getFullMemberName(user.getMember()))
                .addGenericPassword(password)
                .addTypeInformation(typeEmail)
                .addRedirectUrl(urlGuaitil)
                .getTemplate();

        emailSenderService.sendEmail("Envio de datos de la nueva cuenta en Guaitil Tour", emailForm, email, template);
    }
}
