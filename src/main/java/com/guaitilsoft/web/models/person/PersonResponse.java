package com.guaitilsoft.web.models.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PersonResponse {
    private String id;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String telephone;

    private Gender gender;

    private String email;

    private PersonType personType;

}
