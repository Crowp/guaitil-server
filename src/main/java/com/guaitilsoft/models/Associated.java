package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Associated {

    @Id
    @Column(name = "associated_id")
    private Long id;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false,name = "membership_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;

}
