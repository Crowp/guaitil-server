package com.guaitilsoft.web.models.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonView {
    private String id;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String telephone;

    private Gender gender;

    private String email;

    private PersonType personType;

    private Date createdAt;

    private Date updatedAt;
}
