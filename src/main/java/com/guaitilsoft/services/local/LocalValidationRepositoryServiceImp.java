package com.guaitilsoft.services.local;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.utils.EmailNewLocalTemplate;
import com.guaitilsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Primary
@Service("LocalRepositoryServiceValidation")
public class LocalValidationRepositoryServiceImp implements LocalServiceLoad {

    private final LocalRepositoryService localRepositoryService;
    private final MemberRepositoryService memberRepositoryService;
    private final UserRepositoryService userRepositoryService;
    private final EmailSenderService emailSenderService;

    @Value("${user.gmail-sender-email}")
    private String emailForm;

    @Value("${guaitil-domain.client}")
    private String clientDomain;

    @Autowired
    public LocalValidationRepositoryServiceImp(LocalRepositoryService localRepositoryService,
                                               MemberRepositoryService memberRepositoryService,
                                               UserRepositoryService userRepositoryService,
                                               EmailSenderService emailSenderService) {
        this.localRepositoryService = localRepositoryService;
        this.memberRepositoryService = memberRepositoryService;
        this.userRepositoryService = userRepositoryService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Local> list() {
        return localRepositoryService.list();
    }

    @Override
    public Local get(Long id) {
        Local local = localRepositoryService.get(id);
        if (local != null) {
            return local;
        }
        throw new EntityNotFoundException("No se encontró un local con el id: " + id);
    }

    @Override
    public Local getByLocalDescriptionId(Long localDescriptionId) {
        return this.localRepositoryService.getByLocalDescriptionId(localDescriptionId);
    }

    @Override
    public Local save(Local entity) {
        Long memberId = entity.getMember().getId();
        entity.setMember(loadFullMember(memberId));
        Local local = localRepositoryService.save(entity);
        if (local.getMember().getLocals().size() > 1){
            this.sendEmailMemberWithLocal(local);
        }
        return local;
    }

    @Override
    public Local update(Long id, Local entity) {
        if (!id.equals(entity.getId())) {
            throw new ApiRequestException("El id del local: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        return localRepositoryService.update(id, entity);
    }

    @Override
    public List<Local> resetPasswordByLocalId(Long id) {
        Local local = this.get(id);
        Long memberId = local.getMember().getId();
        this.resetPasswordGenericByMemberId(memberId);
        return getAllLocalByIdMember(memberId);
    }

    private void resetPasswordGenericByMemberId(Long memberId){
        User user = userRepositoryService.getByMemberID(memberId);
        userRepositoryService.resetPassword(user.getId(), Utils.getRandomPassword(), true);
    }

    @Override
    public void delete(Long id) {
        Local local = this.get(id);
        Member member = this.memberRepositoryService.get(local.getMember().getId());
        Optional<Local> optionalLocal = member.getLocals().stream().filter(lo -> lo.getId().equals(id)).findFirst();
        optionalLocal.ifPresent(lo -> {
            member.getLocals().remove(lo);
            memberRepositoryService.update(member.getId(), member);
        });
        localRepositoryService.delete(id);
        deleteUserMemberWithoutLocals(member);
    }

    @Override
    public List<Local> getAllLocalByIdMember(Long id) {
        return localRepositoryService.getAllLocalByIdMember(id);
    }

    private Member loadFullMember(Long id){
        return this.memberRepositoryService.get(id);
    }

    private void deleteUserMemberWithoutLocals(Member member){
        if (member.getLocals().isEmpty()){
            userRepositoryService.deleteUserByMemberId(member.getId());
            if (member.getMemberType() == MemberType.REGULAR){
                memberRepositoryService.delete(member.getId());
            }
        }
    }

    private void sendEmailMemberWithLocal(Local local){
        String localName = local.getLocalDescription().getLocalName();
        String email = local.getMember().getPerson().getEmail();
        String template = new EmailNewLocalTemplate()
                .addPersonName(Utils.getFullMemberName(local.getMember()))
                .addLocalName(localName)
                .addRedirectUrl(clientDomain)
                .getTemplate();
        emailSenderService.sendEmail("Nuevo local agregado", emailForm, email, template);
    }
}
