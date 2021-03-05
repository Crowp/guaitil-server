package com.guaitilsoft.web.models.person;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonRequest {
    private String id;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String telephone;

    private Gender gender;

    private String email;

    private PersonType personType;
}
