package com.guaitilsoft.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @Column(name = "tour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "amount_Person")
    private Long amountPerson;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
