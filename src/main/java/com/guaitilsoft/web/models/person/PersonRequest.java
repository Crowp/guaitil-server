package com.guaitilsoft.web.models.person;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PersonRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String firstLastName;

    @NotBlank
    private String secondLastName;

    @NotBlank
    private String telephone;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    private String email;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private PersonType personType;
}
