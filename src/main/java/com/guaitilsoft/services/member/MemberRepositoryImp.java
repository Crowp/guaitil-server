package com.guaitilsoft.services.member;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("MemberRepositoryServiceBasic")
public class MemberRepositoryImp implements MemberRepositoryService{

    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryImp(MemberRepository memberRepository) {
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
        return this.memberRepository.findById(id).orElse(null);
    }

    @Override
    public Member save(Member entity) {
        assert entity != null;

        if (memberRepository.existMemberPersonId(entity.getPerson().getId())) {
            throw new ApiRequestException("Cédula: " + entity.getPerson().getId() + " esta ocupada");
        }
        String email = entity.getPerson().getEmail();
        if (memberRepository.existMemberPersonEmail(email)) {
            throw new ApiRequestException("Email: " + email + " esta ocupado");
        }

        return this.memberRepository.save(entity);
    }

    @Override
    public Member update(Long id, Member entity) {
        assert id != null;
        assert entity != null;

        Member member = this.get(id);
        member.setOccupation(entity.getOccupation());
        member.setLocals(entity.getLocals());
        member.setMemberType(entity.getMemberType());
        member.setPerson(entity.getPerson());
        member.setAffiliationDate(entity.getAffiliationDate());

        return this.memberRepository.save(member);
    }

    @Override
    public void delete(Long id) {
        Member member = this.get(id);
        this.memberRepository.delete(member);
    }

    @Override
    public List<Member> getMemberWithoutUser() {
        return this.memberRepository.getMembersWithoutUser();
    }
}
