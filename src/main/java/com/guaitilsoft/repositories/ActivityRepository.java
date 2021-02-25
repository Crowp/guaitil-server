package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Multimedia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    @Query("SELECT m FROM Multimedia m INNER JOIN Activity a ON a.id=:id")
    List<Multimedia> getActivityMultimedia(@Param("id") Long id);

    @Query("DELETE FROM Multimedia m WHERE m.id=:id")
    void deleteMultimediaById(@Param("id") Long id);
}
