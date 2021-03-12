package com.guaitilsoft.services.local;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.services.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("LocalRepositoryServiceValidation")
public class LocalValidationRepositoryServiceImp implements LocalRepositoryService {

    private final LocalRepositoryService localRepositoryService;
    private final MemberRepositoryService memberRepositoryService;
    private final UserRepositoryService userRepositoryService;

    @Autowired
    public LocalValidationRepositoryServiceImp(LocalRepositoryService localRepositoryService,
                                               MemberRepositoryService memberRepositoryService,
                                               UserRepositoryService userRepositoryService) {
        this.localRepositoryService = localRepositoryService;
        this.memberRepositoryService = memberRepositoryService;
        this.userRepositoryService = userRepositoryService;
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
    public Local save(Local entity) {
        Long memberId = entity.getMember().getId();
        entity.setMember(loadFullMember(memberId));
        return localRepositoryService.save(entity);
    }

    @Override
    public Local update(Long id, Local entity) {
        if (!id.equals(entity.getId())) {
            throw new ApiRequestException("El id del local: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        return localRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        Local local = this.get(id);
        localRepositoryService.delete(id);
        deleteUserMemberWithoutLocals(local);
    }

    @Override
    public List<Local> getAllLocalByIdMember(Long id) {
        return localRepositoryService.getAllLocalByIdMember(id);
    }

    private Member loadFullMember(Long id){
        return this.memberRepositoryService.get(id);
    }

    private void deleteUserMemberWithoutLocals(Local local){
        Member member = memberRepositoryService.get(local.getMember().getId());
        if (member.getLocals().size() == 0){
            userRepositoryService.deleteUserByMemberId(member.getId());
            if (member.getMemberType() == MemberType.REGULAR){
                memberRepositoryService.delete(member.getId());
            }
        }
    }
}
