package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Tour implements Serializable {

    @Id
    @Column(name = "tour_id")
    private Long id;

    @Column(nullable = false,name = "amount_Person")
    private int amountPerson;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
