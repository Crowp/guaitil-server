package com.guaitilsoft.web.models.reservation;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.tour.LoadTour;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationView {

    private Long id;

    private Date dateReservation;

    private Long amountPerson;

    private ReservationState reservationState;

    private Date createdAt;

    private Date updatedAt;

    private LoadTour tour;

    private Person person;
}
