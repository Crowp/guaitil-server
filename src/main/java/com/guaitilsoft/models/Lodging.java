package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Lodging {

    @Id
    @Column
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Local local;
}
