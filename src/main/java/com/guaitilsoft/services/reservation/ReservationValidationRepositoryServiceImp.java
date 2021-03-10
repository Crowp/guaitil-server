package com.guaitilsoft.services.reservation;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.services.activityDescription.ActivityDesRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("ReservationRepositoryServiceValidation")
public class ReservationValidationRepositoryServiceImp implements ReservationRepositoryService {
    private final ReservationRepositoryService reservationRepositoryService;
    private final PersonService personService;
    private final ActivityDesRepositoryService activityDesRepositoryService;

    @Autowired
    public ReservationValidationRepositoryServiceImp(@Qualifier("ReservationRepositoryServiceBasic") ReservationRepositoryService reservationRepositoryService, PersonService personService, ActivityDesRepositoryService activityDesRepositoryService) {
        this.reservationRepositoryService = reservationRepositoryService;
        this.personService = personService;
        this.activityDesRepositoryService = activityDesRepositoryService;
    }

    @Override
    public List<Reservation> list() {
        return reservationRepositoryService.list();
    }

    @Override
    public Reservation get(Long id) {
        Reservation reservation = reservationRepositoryService.get(id);
        if(reservation != null){
            return reservation;
        }
        throw new EntityNotFoundException("No se encontró la reservación con el id: " + id);
    }

    @Override
    public Reservation save(Reservation entity) {
        String personId = entity.getPerson().getId();
        if (personService.existPerson(personId)){
            entity.setPerson(personService.get(personId));
        }
        return reservationRepositoryService.save(entity);
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id de la reservacion: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        getActivityDescription(entity);
        return reservationRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        reservationRepositoryService.delete(id);
    }

    @Override
    public void deleteReservationsByTourId(Long idTour) {
        reservationRepositoryService.deleteReservationsByTourId(idTour);
    }

    @Override
    public void deleteReservationByPersonId(String idPerson) {
        reservationRepositoryService.deleteReservationByPersonId(idPerson);
    }

    private void getActivityDescription(Reservation reservation){
        reservation.setActivityDescription(this.activityDesRepositoryService.get(reservation.getActivityDescription().getId()));
    }
}
