package com.guaitilsoft.services.reservation;

import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.web.models.reservation.ReservationRequest;
import com.guaitilsoft.web.models.reservation.ReservationResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ReservationServiceImp implements ReservationService{
    private final ReservationRepositoryService reservationRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationServiceImp(ReservationRepositoryService reservationRepositoryService,
                                 ModelMapper modelMapper) {
        this.reservationRepositoryService = reservationRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReservationResponse> list() {
        return this.parseToListReservationResponses(reservationRepositoryService.list());
    }

    @Override
    public ReservationResponse get(Long id) {
        return this.parseToReservationResponse(reservationRepositoryService.get(id));
    }

    @Override
    public ReservationResponse save(ReservationRequest entity) {
        Reservation reservation = this.parseToReservation(entity);
        return onSaveReservationResponse(reservation);
    }
    private ReservationResponse onSaveReservationResponse (Reservation reservationToStore){
        Reservation reservation = reservationRepositoryService.save(reservationToStore);
        return this.parseToReservationResponse(reservation);
    }

    @Override
    public ReservationResponse update(Long id, ReservationRequest entity) {
        return parseToReservationResponse(reservationRepositoryService.update(id, this.parseToReservation(entity)));
    }

    @Override
    public void delete(Long id) { reservationRepositoryService.delete(id);  }

    @Override
    public List<Reservation> listReservation() {
        return reservationRepositoryService.list();
    }

    private List<ReservationResponse> parseToListReservationResponses (List<Reservation> reservationList){
        Type lisType = new TypeToken<List<ReservationResponse>>(){}.getType();
        return modelMapper.map(reservationList, lisType);
    }
    private ReservationResponse parseToReservationResponse (Reservation reservation){
        return modelMapper.map(reservation, ReservationResponse.class);
    }
    private Reservation parseToReservation (ReservationRequest reservationRequest){
        return modelMapper.map(reservationRequest, Reservation.class);
    }
}
