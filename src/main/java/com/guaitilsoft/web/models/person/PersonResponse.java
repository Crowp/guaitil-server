package com.guaitilsoft.web.models.person;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class PersonResponse {

    private String id;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String telephone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;

    @Enumerated(EnumType.STRING)
    private PersonType personType;
}
