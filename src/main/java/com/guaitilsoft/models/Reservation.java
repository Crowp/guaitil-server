package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
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
    private ReservationState reservationState;

    @OneToOne(cascade = CascadeType.ALL)
    private Tour tour;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;
}
