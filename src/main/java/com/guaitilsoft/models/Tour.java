package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Tour {

    @Id
    @Column
    private Long id;

    @Column(nullable = false,name = "amount_Person")
    private int amountPerson;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

}
