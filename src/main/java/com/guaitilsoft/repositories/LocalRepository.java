package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.constant.LocalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalRepository extends CrudRepository<Local, Long> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Local l WHERE l.member.person.id = :id AND l.localDescription.localType = :localType")
    boolean existMemberPersonLocal(@Param("id") String id, @Param("localType")LocalType localType);

    @Query("SELECT l FROM Local l WHERE l.member.id =:id")
    Iterable<Local> getAllLocalByIdMember(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Local l WHERE l.member.id =:memberId AND l.id<>:localId AND l.localDescription.localType =:localType")
    boolean memberHaveLocalWithType(@Param("memberId") Long memberId, @Param("localId") Long localId, @Param("localType") LocalType localType);

    @Query("SELECT l FROM Local l WHERE l.localDescription.id =:id")
    Optional<Local> getByLocalDescriptionId(@Param("id") Long localDescriptionId);
}
