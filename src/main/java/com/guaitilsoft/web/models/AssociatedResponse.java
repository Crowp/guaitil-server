package com.guaitilsoft.web.models;

import com.guaitilsoft.models.Person;
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
}
