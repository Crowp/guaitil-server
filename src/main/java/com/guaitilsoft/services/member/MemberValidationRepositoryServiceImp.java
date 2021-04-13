package com.guaitilsoft.services.member;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.utils.EmailNewAssociatedTemplate;
import com.guaitilsoft.utils.GuaitilEmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("MemberRepositoryServiceValidation")
public class MemberValidationRepositoryServiceImp implements MemberRepositoryService {

    private final MemberRepositoryService memberRepositoryService;
    private final UserRepositoryService userRepositoryService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public MemberValidationRepositoryServiceImp(MemberRepositoryService memberRepositoryService,
                                                @Qualifier("UserRepositoryServiceBasic") UserRepositoryService userRepositoryService,
                                                EmailSenderService emailSenderService) {
        this.memberRepositoryService = memberRepositoryService;
        this.userRepositoryService = userRepositoryService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Member> list() {
        return memberRepositoryService.list();
    }

    @Override
    public Member get(Long id) {
        Member member = memberRepositoryService.get(id);
        if (member != null) {
            return member;
        }
        throw new EntityNotFoundException("No se encontró un asociado con el id: " + id);
    }

    @Override
    public Member save(Member entity) {
        entity.setId(null);
        Member member = this.memberRepositoryService.save(entity);
        if (member.getLocals().isEmpty() && member.getMemberType().equals(MemberType.ASSOCIATED)){
            sendEmailAssociated(member);
        }
        return member;
    }

    @Override
    public Member update(Long id, Member entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id del miembro: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        return this.memberRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        if (this.memberHaveUser(id)){
            User user = userRepositoryService.getByMemberID(id);
            if (!user.getRoles().contains(Role.ROLE_ADMIN)){
                this.userRepositoryService.deleteUserByMemberId(id);
                this.memberRepositoryService.delete(id);
            }else {
                throw new ApiRequestException("No puedes eliminar un miembro administrador");
            }
        }else {
            this.memberRepositoryService.delete(id);
        }
    }

    @Override
    public List<Member> getMemberWithoutUser() {
        return this.memberRepositoryService.getMemberWithoutUser();
    }

    @Override
    public Boolean memberHaveUser(Long id) {
        return this.memberRepositoryService.memberHaveUser(id);
    }

    private void sendEmailAssociated(Member member) {
        String name = member.getPerson().getName();
        String lastname = member.getPerson().getFirstLastName();
        String secondLastname = member.getPerson().getSecondLastName();
        String email = member.getPerson().getEmail();
        String template = new EmailNewAssociatedTemplate()
                .addFullName(name + " " + lastname + " " + secondLastname)
                .getTemplate();

        emailSenderService.sendEmail("Envio de datos de la nueva cuenta en Guaitil Tour", GuaitilEmailInfo.getEmailFrom(), email, template);
    }
}
