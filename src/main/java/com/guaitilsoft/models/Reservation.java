package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Reservation {

    @Id
    @Column
    private Long id;

    @Column(nullable = false,name = "sale_reservation")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleReservation;

    @Column(nullable = false,name = "amount_Person")
    private Long amountPerson;

    @Column(nullable = false, name = "client_name")
    private  String clientName;

    @Column(nullable = false, name = "total_amount")
    private Float totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    private Tour tour;
}
