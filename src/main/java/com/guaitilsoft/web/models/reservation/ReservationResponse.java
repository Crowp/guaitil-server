package com.guaitilsoft.web.models.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReservationState;
import com.guaitilsoft.web.models.activity.ActivityLazyResponse;
import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionLazyResponse;
import com.guaitilsoft.web.models.person.PersonRequest;
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

    private ActivityDescriptionLazyResponse activityDescription;

    private PersonRequest person;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
