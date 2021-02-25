package com.guaitilsoft.web.models.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.activity.ActivityView;
import com.guaitilsoft.web.models.activity.LoadActivityView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationView {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateReservation;

    private Long amountPerson;

    private ReservationState reservationState;

    private LoadActivityView tour;

    private Person person;
}
