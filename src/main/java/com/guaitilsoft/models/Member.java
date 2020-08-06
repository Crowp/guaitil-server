package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@Entity
public class Member implements Serializable {

    @Id
    @Column
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

}
