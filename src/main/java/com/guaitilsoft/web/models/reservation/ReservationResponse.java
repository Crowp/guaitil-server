package com.guaitilsoft.web.models.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionResponse;
import com.guaitilsoft.web.models.person.PersonDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateReservation;

    private Long amountPerson;

    private ReservationState reservationState;

    private ActivityDescriptionResponse activityDescription;

    private PersonDTO person;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
