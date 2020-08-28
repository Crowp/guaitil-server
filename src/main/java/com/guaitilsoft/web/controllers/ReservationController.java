package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.services.ReservationService;
import com.guaitilsoft.web.models.reservation.ReservationView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reservation")
public class ReservationController {

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private ReservationService reservationService;
    private ModelMapper modelMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ModelMapper modelMapper){
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> get () {return ResponseEntity.ok().body(reservationService.list());}

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationService.get(id));
    }

    @PostMapping
    public ResponseEntity<ReservationView> post(@RequestBody ReservationView reservationRequest) throws  Exception{
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        logger.info("Creating reservation: {}", reservation);
        reservationService.save(reservation);
        ReservationView reservationResponse = modelMapper.map(reservation, ReservationView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();
        logger.info("Created reservation : {}", reservationResponse.getId());

        return ResponseEntity.created(location).body(reservationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationView> put(@PathVariable Long id, @RequestBody ReservationView reservationRequest) throws Exception, EntityNotFoundException {
        if(!id.equals(reservationRequest.getId())){
            throw new ApiRequestException("El id de la reservacion: " + reservationRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        logger.info("Updating Reservation with id {}", id);
        reservationService.update(id, reservation);
        ReservationView reservationResponse = modelMapper.map(reservation, ReservationView.class);
        logger.info("Updated Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReservationView> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        ReservationView reservationResponse = modelMapper.map(reservationService.get(id), ReservationView.class);
        logger.info("Deleting Reservation with id {}", id);
        reservationService.delete(id);
        logger.info("Deleted Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }
}
