package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Multimedia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends CrudRepository<Multimedia, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Multimedia m WHERE m.fileName = :fileName")
    boolean existFileName(@Param("fileName") String fileName);
}
