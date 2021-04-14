package com.guaitilsoft.services.member;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.services.user.UserService;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.member.MemberResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberRepositoryService memberRepositoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImp(MemberRepositoryService memberRepositoryService,
                            UserService userService,
                            ModelMapper modelMapper) {
        this.memberRepositoryService = memberRepositoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MemberResponse> list() {
        return this.parseToMemberResponseList(memberRepositoryService.list());
    }

    @Override
    public List<MemberResponse> getAllMembersWithoutAdmins() {
        List<MemberResponse> members = new ArrayList<>();
        this.list().forEach(member -> this.userService.getAllUsers().forEach(user -> {
            if (user.getMember().getId().equals(member.getId())){
                if (!user.getRoles().contains(Role.ROLE_ADMIN) && !user.getRoles().contains(Role.ROLE_SUPER_ADMIN)){
                    members.add(member);
                }
            }
        }));
        return members;
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
