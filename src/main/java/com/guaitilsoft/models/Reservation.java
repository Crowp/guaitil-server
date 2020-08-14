package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReservationState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateReservation;

    @NotEmpty
    private Long amountPerson;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    @OneToOne
    private Tour tour;

    @OneToOne
    private Person person;
}
