package com.guaitilsoft.services;

import com.guaitilsoft.models.Member;

import java.io.OutputStream;
import java.util.List;

public interface MemberService {
    List<Member> list();

    Member get(Long id);

    void save(Member entity);

    void update(Long id, Member entity);

    void delete(Long id);

    List<Member> getMemberWithoutUser();

    void exportPdf(OutputStream outputStream, List<Member> members);

}
