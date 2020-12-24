package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.repositories.ReservationRepository;
import com.guaitilsoft.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImp implements ReservationService {

    private final ReservationRepository reservationRepository;


    @Autowired
    public ReservationServiceImp(ReservationRepository reservationRepository){
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

        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if(reservation != null){
            return reservation;
        }
        throw new EntityNotFoundException("No se encontro la reservacion con el id: ");
    }

    @Override
    public void save(Reservation entity) {
        assert entity != null;
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        reservationRepository.save(entity);
    }

    @Override
    public void update(Long id, Reservation entity) {
        assert id != null;
        assert entity != null;

        Reservation reservation = this.get(id);
        reservation.setDateReservation(entity.getDateReservation());
        reservation.setAmountPerson(entity.getAmountPerson());
        reservation.setReservationState(entity.getReservationState());
        reservation.setActivity(entity.getActivity());
        reservation.setPerson(entity.getPerson());
        reservation.setUpdatedAt(new Date());
        reservationRepository.save(reservation);
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
