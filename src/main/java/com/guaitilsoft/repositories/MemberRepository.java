package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.id = :id")
    boolean existMemberPersonId(@Param("id") String id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.email = :email")
    boolean existMemberPersonEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (SELECT u.member.id FROM User u)")
    List<Member> getMembersWithoutUser();

    @Query("SELECT m FROM Member m INNER JOIN User u ON u.roles=:role")
    List<Member> getMembersByRole(@Param("role") Role role);
}
