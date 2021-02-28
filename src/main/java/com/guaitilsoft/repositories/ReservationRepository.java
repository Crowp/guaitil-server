package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r INNER JOIN Activity a ON a.activityDescription.id = r.activityDescription.id WHERE a.id= :id")
    Optional<List<Reservation>> selectReservationsByActivityId(@Param("id") Long id);

    @Query("SELECT r FROM Reservation r WHERE r.person.id = :id")
    Optional<Reservation> selectReservationByPersonId(@Param("id") String id);
}
