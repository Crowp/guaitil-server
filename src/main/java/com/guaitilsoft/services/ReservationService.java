package com.guaitilsoft.services;

import com.guaitilsoft.models.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> list();

    Reservation get(Long id);

    void save(Reservation entity);

    void update(Long id, Reservation entity);

    void delete(Long id);

    void deleteReservationsByTourId(Long idTour);

    void deleteReservationByPersonId(String idPerson);

}
