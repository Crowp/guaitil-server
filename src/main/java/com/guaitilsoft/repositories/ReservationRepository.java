package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

}
