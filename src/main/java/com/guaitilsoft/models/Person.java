package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String firstLastName;

    @NotEmpty
    private String secondLastName;

    @NotEmpty
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private PersonType personType;
}
