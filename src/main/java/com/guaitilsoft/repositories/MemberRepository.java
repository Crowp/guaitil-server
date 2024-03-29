package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.id = :id")
    boolean existMemberPersonId(@Param("id") String id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.email = :email")
    boolean existMemberPersonEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m, User u WHERE m.id=:id AND m.id=u.member.id")
    boolean memberHaveUser(@Param("id") Long id);

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (SELECT u.member.id FROM User u)")
    List<Member> getMembersWithoutUser();
}
