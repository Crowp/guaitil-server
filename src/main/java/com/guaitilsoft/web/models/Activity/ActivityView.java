package com.guaitilsoft.web.models.Activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ActivityView {
    private Long id;

    private String name;

    private String description;

    private Date activityDate;

    private Address address;

    private List<Local> locals;

    private List<Multimedia> multimedia;
}
