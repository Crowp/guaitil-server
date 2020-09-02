package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {
    @Query("SELECT t FROM Tour t WHERE t.activity.id = :id")
    Optional<Tour> selectTourByActivityId(@Param("id") Long id);

}
