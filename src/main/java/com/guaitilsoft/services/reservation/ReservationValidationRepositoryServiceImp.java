package com.guaitilsoft.services.reservation;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.models.constant.TypeEmail;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.activityDescription.ActivityDesRepositoryService;
import com.guaitilsoft.services.person.PersonRepositoryService;
import com.guaitilsoft.utils.EmailReservationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Primary
@Service("ReservationRepositoryServiceValidation")
public class ReservationValidationRepositoryServiceImp implements ReservationRepositoryService {

    private final ReservationRepositoryService reservationRepositoryService;
    private final PersonRepositoryService personRepositoryService;
    private final ActivityDesRepositoryService activityDesRepositoryService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ReservationValidationRepositoryServiceImp(ReservationRepositoryService reservationRepositoryService,
                                                     PersonRepositoryService personRepositoryService,
                                                     ActivityDesRepositoryService activityDesRepositoryService,
                                                     EmailSenderService emailSenderService) {
        this.reservationRepositoryService = reservationRepositoryService;
        this.personRepositoryService = personRepositoryService;
        this.activityDesRepositoryService = activityDesRepositoryService;
        this.emailSenderService = emailSenderService;
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
        if (personRepositoryService.existPerson(personId)){
            entity.setPerson(personRepositoryService.get(personId));
        }
        entity.setActivityDescription(activityDesRepositoryService.get(entity.getActivityDescription().getId()));
        Reservation reservation = reservationRepositoryService.save(entity);
        sendEmailReservationClient(reservation);
        return reservation;
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id de la reservacion: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        getActivityDescription(entity);
        Reservation reservation = reservationRepositoryService.update(id, entity);
        sendEmailReservationClient(reservation);
        return reservation;
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

    private void sendEmailReservationClient(Reservation reservation) {
        String personName = reservation.getPerson().getName().concat(" ").concat(reservation.getPerson().getFirstLastName()).concat(" ").concat(reservation.getPerson().getSecondLastName());
        String activityName = reservation.getActivityDescription().getName();
        LocalDateTime activityDate = reservation.getActivityDescription().getActivityDate();
        Double personPrice = reservation.getActivityDescription().getPersonPrice();
        String activityAddress = reservation.getActivityDescription().getAddress().getPhysicalAddress();
        Long amountPerson = reservation.getAmountPerson();
        LocalDateTime reservationDate = reservation.getDateReservation();

        String template = new EmailReservationClient()
                .addPersonName(personName)
                .addActivityName(activityName)
                .addActivityDate(activityDate)
                .addPersonPrice(personPrice)
                .addActivityAddress(activityAddress)
                .addAmountPerson(amountPerson)
                .addReservationDate(reservationDate)
                .addTypeInformation(TypeEmail.RESERVATION_CLIENT)
                .getTemplate();

        emailSenderService.sendEmail("Reservación del tour "+activityName+", GuaitilTour", "guaitiltour.cr@gmail.com", reservation.getPerson().getEmail(), template);
    }
}
