package com.guaitilsoft.services.member;

import com.guaitilsoft.models.Member;

import java.util.List;

public interface MemberRepositoryService {
    List<Member> list();

    Member get(Long id);

    Member save(Member entity);

    Member update(Long id, Member entity);

    void delete(Long id);

    List<Member> getMemberWithoutUser();
}
