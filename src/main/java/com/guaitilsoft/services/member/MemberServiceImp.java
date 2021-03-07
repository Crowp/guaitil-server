package com.guaitilsoft.services.member;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.member.MemberResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberRepositoryService memberRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImp(@Qualifier("MemberRepositoryServiceValidation") MemberRepositoryService memberRepositoryService, ModelMapper modelMapper) {
        this.memberRepositoryService = memberRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MemberResponse> list() {
        return this.parseToMemberResponseList(memberRepositoryService.list());
    }

    @Override
    public MemberResponse get(Long id) {
        return this.parseToMemberResponse(memberRepositoryService.get(id));
    }

    @Override
    public MemberResponse save(MemberRequest entity) {
        Member member = this.parseToMember(entity);
        return onSaveMember(member);
    }

    private MemberResponse onSaveMember(Member memberToStore){
        Member member = memberRepositoryService.save(memberToStore);
        return this.parseToMemberResponse(member);
    }

    @Override
    public MemberResponse update(Long id, MemberRequest entity) {
        return this.parseToMemberResponse(memberRepositoryService.update(id, this.parseToMember(entity)));
    }

    @Override
    public void delete(Long id) {
        memberRepositoryService.delete(id);
    }

    @Override
    public List<MemberResponse> getMemberWithoutUser() {
        return this.parseToMemberResponseList(memberRepositoryService.getMemberWithoutUser());
    }

    @Override
    public List<Member> memberList() {
        return memberRepositoryService.list();
    }

    private List<MemberResponse> parseToMemberResponseList(List<Member> members){
        Type listType = new TypeToken<List<MemberResponse>>(){}.getType();
        return this.modelMapper.map(members, listType);
    }

    private Member parseToMember(MemberRequest memberRequest){
        return this.modelMapper.map(memberRequest, Member.class);
    }

    private MemberResponse parseToMemberResponse(Member member){
        return this.modelMapper.map(member, MemberResponse.class);
    }
}
