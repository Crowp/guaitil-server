package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tour implements Serializable {

    @Id
    @Column(name = "tour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "amount_Person")
    private Long amountPerson;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
