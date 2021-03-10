package com.guaitilsoft.services.reservation;

import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.web.models.reservation.ReservationRequest;
import com.guaitilsoft.web.models.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {
    List<ReservationResponse> list();

    ReservationResponse get(Long id);

    ReservationResponse save(ReservationRequest entity);

    ReservationResponse update(Long id, ReservationRequest entity);

    void delete(Long id);

    List<Reservation> listReservation();
}
