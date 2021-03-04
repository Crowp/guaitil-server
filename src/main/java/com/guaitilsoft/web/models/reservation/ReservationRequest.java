package com.guaitilsoft.web.models.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.activity.ActivityLazyResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateReservation;

    private Long amountPerson;

    private ReservationState reservationState;

    private ActivityLazyResponse tour;

    private Person person;
}
