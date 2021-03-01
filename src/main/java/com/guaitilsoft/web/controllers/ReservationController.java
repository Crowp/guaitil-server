package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.services.ReservationService;
import com.guaitilsoft.web.models.reservation.ReservationResponse;
import com.guaitilsoft.web.models.reservation.ReservationRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationController {

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ModelMapper modelMapper, PersonService personService){
        this.reservationService = reservationService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> get () {
        Type listType  = new TypeToken<List<ReservationResponse>>(){}.getType();
        List<ReservationResponse> reservations = modelMapper.map(reservationService.list(),listType);
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable Long id) {
        ReservationResponse reservations = modelMapper.map(reservationService.get(id), ReservationResponse.class);
        logger.info("Fetching Reservation with id {}", id);
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationRequest> post(@RequestBody ReservationRequest reservationRequest){
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        logger.info("Creating reservation: {}", reservation);
        String personId = reservation.getPerson().getId();
        if (personService.existPerson(personId)){
            reservation.setPerson(personService.get(personId));
        }
        reservationService.save(reservation);
        ReservationRequest reservationResponse = modelMapper.map(reservation, ReservationRequest.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();
        logger.info("Created reservation : {}", reservationResponse.getId());

        return ResponseEntity.created(location).body(reservationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationRequest> put(@PathVariable Long id, @RequestBody ReservationRequest reservationRequest) {
        if(!id.equals(reservationRequest.getId())){
            throw new ApiRequestException("El id de la reservacion: " + reservationRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        logger.info("Updating Reservation with id {}", id);
        reservationService.update(id, reservation);
        ReservationRequest reservationResponse = modelMapper.map(reservation, ReservationRequest.class);
        logger.info("Updated Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReservationRequest> delete(@PathVariable Long id) {
        ReservationRequest reservationResponse = modelMapper.map(reservationService.get(id), ReservationRequest.class);
        logger.info("Deleting Reservation with id {}", id);
        reservationService.delete(id);
        logger.info("Deleted Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }
}
