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
public class Lodging implements Serializable {

    @Id
    @Column(name = "lodging_id")
    private Long id;

    @Column(nullable = false)
    private Long space;

    @OneToOne(cascade = CascadeType.ALL)
    private Local local;
}
