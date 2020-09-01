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
    @Query("SELECT r FROM Reservation r WHERE r.tour.id = :id")
    Optional<List<Reservation>> selectReservationsByTourId(@Param("id") Long id);

}
