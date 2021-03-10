package com.guaitilsoft.services.reservation;

import com.guaitilsoft.models.Reservation;

import java.util.List;

public interface ReservationRepositoryService {
    List<Reservation> list();

    Reservation get(Long id);

    Reservation save(Reservation entity);

    Reservation update(Long id, Reservation entity);

    void delete(Long id);

    void deleteReservationsByTourId(Long idTour);

    void deleteReservationByPersonId(String idPerson);

}
