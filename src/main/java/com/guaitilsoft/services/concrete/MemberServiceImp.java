package com.guaitilsoft.services.concrete;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.repositories.MemberRepository;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;
    private final LocalService localService;
    private final UserService userService;


    @Autowired
    public MemberServiceImp(
            MemberRepository memberRepository,
            LocalService localService,
            UserService userService) {
        this.memberRepository = memberRepository;
        this.localService = localService;
        this.userService = userService;
    }

    @Override
    public List<Member> list() {
        Iterable<Member> iterable = memberRepository.findAll();
        List<Member> members = new ArrayList<>();
        iterable.forEach(members::add);
        return members;
    }

    @Override
    public Member get(Long id) {
        assert id != null;

        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            return member;
        }
        throw new EntityNotFoundException("No se encontro un asociado con el id: " + id);
    }

    @Override
    public void save(Member entity) {
        assert entity != null;

        if (memberRepository.existMemberPersonId(entity.getPersonId())) {
            throw new ApiRequestException("Cedula: " + entity.getPersonId() + " esta ocupada");
        }
        if (memberRepository.existMemberPersonEmail(entity.getEmail())) {
            throw new ApiRequestException("Email: " + entity.getEmail() + " esta ocupado");
        }
        entity.setUpdatedAt(new Date());
        memberRepository.save(entity);
    }

    @Override
    public void update(Long id, Member entity) {
        assert id != null;
        assert entity != null;

        Member member = this.get(id);
        member.setOccupation(entity.getOccupation());
        member.setLocals(entity.getLocals());
        member.setMemberType(entity.getMemberType());
        member.setPerson(entity.getPerson());
        member.setUpdatedAt(new Date());
        member.setCreatedAt(entity.getCreatedAt());

        memberRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Member member = this.get(id);
        List<Local> locals = new ArrayList<>(member.getLocals());
        member.setLocals(null);
        memberRepository.save(member);
        if (locals.size() > 0) {
            locals.forEach(local -> {
                localService.delete(local.getId());
            });
        }
        userService.deleteUserByMemberId(id);
        memberRepository.delete(member);
    }

    @Override
    public List<Member> getMemberWithoutUser() {
        Iterable<Member> iterable = memberRepository.membersWithoutUser(MemberType.ASSOCIATED);
        List<Member> members = new ArrayList<>();
        iterable.forEach(members::add);
        return members;
    }
}
