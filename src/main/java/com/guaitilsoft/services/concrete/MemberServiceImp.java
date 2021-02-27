package com.guaitilsoft.services.concrete;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.repositories.MemberRepository;
import com.guaitilsoft.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
        throw new EntityNotFoundException("No se encontró un asociado con el id: " + id);
    }

    @Override
    public Member save(Member entity) {
        assert entity != null;

        if (memberRepository.existMemberPersonId(entity.getDni())) {
            throw new ApiRequestException("Cédula: " + entity.getDni() + " esta ocupada");
        }
        String email = entity.getPerson().getEmail();
        if (memberRepository.existMemberPersonEmail(email)) {
            throw new ApiRequestException("Email: " + email + " esta ocupado");
        }
        return memberRepository.save(entity);
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

        memberRepository.save(member);
    }

    @Override
    public void delete(Long id) {
        assert id != null;
        Member member = this.get(id);
        memberRepository.delete(member);
    }

    @Override
    public List<Member> getMemberWithoutUser() {
        return memberRepository.getMembersWithoutUser();
    }
}
