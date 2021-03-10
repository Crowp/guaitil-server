package com.guaitilsoft.services.reservation;

import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ReservationRepositoryServiceBasic")
public class ReservationRepositoryServiceImp implements ReservationRepositoryService {

    private final ReservationRepository reservationRepository;


    @Autowired
    public ReservationRepositoryServiceImp(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> list() {
        Iterable<Reservation> iterable = reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();
        iterable.forEach(reservations::add);

        return reservations;
    }

    @Override
    public Reservation get(Long id) {
        assert id != null;
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation save(Reservation entity) {
        assert entity != null;
        return reservationRepository.save(entity);
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        assert id != null;
        assert entity != null;

        Reservation reservation = this.get(id);
        reservation.setDateReservation(entity.getDateReservation());
        reservation.setAmountPerson(entity.getAmountPerson());
        reservation.setReservationState(entity.getReservationState());
        reservation.setActivityDescription(entity.getActivityDescription());
        reservation.setPerson(entity.getPerson());
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Reservation reservation = this.get(id);
        reservationRepository.delete(reservation);
    }

    @Override
    public void deleteReservationsByTourId(Long idTour) {
        Optional<List<Reservation>> optionalReservations = reservationRepository.selectReservationsByActivityId(idTour);
        optionalReservations.ifPresent(reservations -> reservations.forEach(reservation -> this.delete(reservation.getId())));
    }

    @Override
    public void deleteReservationByPersonId(String idPerson) {
        Optional<Reservation> reservationOptional = reservationRepository.selectReservationByPersonId(idPerson);
        reservationOptional.ifPresent(reservation -> this.delete(reservation.getId()));
    }
}
