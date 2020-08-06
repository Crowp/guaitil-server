package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Activity {

    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,name = "activity_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activityDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

}
