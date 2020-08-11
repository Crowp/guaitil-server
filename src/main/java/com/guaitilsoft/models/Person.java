package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Table(name = "People")
public class Person implements Serializable {

    @Id
    @Column(name = "person_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "first_last_name")
    private String firstLastName;

    @Column(nullable = false, name = "second_last_name")
    private String secondLastName;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonType personType;
}
