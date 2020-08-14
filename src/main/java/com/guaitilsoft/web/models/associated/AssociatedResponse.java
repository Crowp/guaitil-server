package com.guaitilsoft.web.models.associated;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.web.models.person.PersonResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AssociatedResponse {

    private Long id;

    private String occupation;

    private Date membershipDate;

    private PersonResponse person;
}
