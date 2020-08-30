package com.guaitilsoft.services.concrete;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.repositories.MemberRepository;
import com.guaitilsoft.services.MemberService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImp implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> list() {
        Iterable<Member> iterable = memberRepository.findAll();
        List<Member> associates = new ArrayList<>();
        iterable.forEach(associates::add);
        return associates;
    }

    @Override
    public Member get(Long id) {
        assert id != null;

        Member member = memberRepository.findById(id).orElse(null);
        if(member != null){
            return member;
        }
        throw new EntityNotFoundException("No se encontro un asociado con el id: " + id);
    }

    @Override
    public void save(Member entity) {
        assert entity != null;

        if(memberRepository.existMemberPersonId(entity.getPersonId())){
          throw new ApiRequestException("Cedula: " + entity.getPersonId() + " esta ocupada");
        }
        if(memberRepository.existMemberPersonEmail(entity.getEmail())){
            throw new ApiRequestException("Email: " + entity.getEmail() + " esta ocupado");
        }

        memberRepository.save(entity);
    }

    @Override
    public void update(Long id, Member entity) {
        assert id != null;
        assert entity != null;
        Instant nowGmt = Instant.now();
        DateTimeZone americaCostaRica = DateTimeZone.forID("America/Costa_Rica");
        DateTime nowCostaRica = nowGmt.toDateTime(americaCostaRica);
        Date today = nowCostaRica.toDate();

        Member member = this.get(id);
        member.setOccupation(entity.getOccupation());
        member.setLocals(entity.getLocals());
        member.setMemberType(entity.getMemberType());
        member.setPerson(entity.getPerson());
        member.setUpdatedAt(today);

        memberRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Member member = this.get(id);
        if(member != null){
            memberRepository.delete(member);
        }
    }
}
