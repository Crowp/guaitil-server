package com.guaitilsoft.services.member;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.user.UserRepositoryService;
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

    @Autowired
    public MemberValidationRepositoryServiceImp(MemberRepositoryService memberRepositoryService,
                                                @Qualifier("UserRepositoryServiceBasic") UserRepositoryService userRepositoryService) {
        this.memberRepositoryService = memberRepositoryService;
        this.userRepositoryService = userRepositoryService;
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
        return this.memberRepositoryService.save(entity);
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
        User user = userRepositoryService.getByMemberID(id);
        if (!user.getRoles().contains(Role.ROLE_ADMIN)){
            this.userRepositoryService.deleteUserByMemberId(id);
            this.memberRepositoryService.delete(id);
        }
        throw new ApiRequestException("No puedes eliminar un miembro administrador");
    }

    @Override
    public List<Member> getMemberWithoutUser() {
        return this.memberRepositoryService.getMemberWithoutUser();
    }
}
