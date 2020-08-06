package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "People")
public abstract class Person implements Serializable {

    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "first_last_name")
    private String firstLastName;

    @Column(nullable = false, name = "second_last_name")
    private String secondLastName;

    @Column(nullable = false)
    private String telephone;

    public Person() {
    }

    public Person(String id, String name, String firstLastName, String secondLastName, String telephone){
        this.id = id;
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.telephone = telephone;
    }
}
