package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.PersonType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private String id;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String telephone;

    private String email;

    @Enumerated(EnumType.STRING)
    private PersonType personType;
}
