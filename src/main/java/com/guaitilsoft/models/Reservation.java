package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReservationState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation implements Serializable {

    @Id
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false,name = "date_reservation")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateReservation;

    @Column(nullable = false,name = "amount_person")
    private Long amountPerson;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    @OneToOne(cascade = CascadeType.ALL)
    private Tour tour;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
}
