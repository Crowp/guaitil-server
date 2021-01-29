package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.PersonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.id = :id")
    boolean existMemberPersonId(@Param("id") String id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.person.email = :email")
    boolean existMemberPersonEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (SELECT u.member.id FROM User u) AND m.memberType =:memberType")
    Iterable<Member> membersWithoutUser(@Param("memberType")MemberType memberType);

    @Query("SELECT p.firstLastName, p.secondLastName, p.name, p.id, m.occupation, p.telephone FROM Member m INNER JOIN Person p ON m.person.id = p.id WHERE p.personType =: personType")
    Iterable<Object> memberReport(@Param("personType") PersonType personType);
}
