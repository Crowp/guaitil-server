package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(updatable = false, nullable = false)
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
