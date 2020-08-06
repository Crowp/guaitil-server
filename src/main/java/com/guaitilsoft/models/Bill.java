package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Bill implements Serializable {
    @Id
    @Column
    private int id;

    @Column(nullable = false, name = "total_amount")
    private Float totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    private Reservation reservation;

}
