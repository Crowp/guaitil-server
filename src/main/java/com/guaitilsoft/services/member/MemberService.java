package com.guaitilsoft.services.member;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.member.MemberResponse;

import java.util.List;

public interface MemberService {
    List<MemberResponse> list();

    List<MemberResponse> getAllMembersWithoutAdmins();

    MemberResponse get(Long id);

    MemberResponse save(MemberRequest entity);

    MemberResponse update(Long id, MemberRequest entity);

    void delete(Long id);

    List<MemberResponse> getMemberWithoutUser();

    List<Member> memberList();
}
