package com.guaitilsoft.repositories;

import com.guaitilsoft.models.User;
import com.guaitilsoft.models.constant.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.member.person.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.member.person.email = :email")
    User findByEmail(@Param("email") String email);

    @Transactional
    @Query("DELETE FROM User u WHERE u.member.person.email = :email")
    void deleteByEmail(@Param("email") String email);
}
