package com.guaitilsoft.web.models.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.activity.LoadActivityView;
import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetReservation {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateReservation;

    private Long amountPerson;

    private ReservationState reservationState;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private LoadActivityView activity;

    private PersonRequest person;
}
