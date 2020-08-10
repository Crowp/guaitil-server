package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Associated implements Serializable {

    @Id
    @Column(name = "associated_id")
    private Long id;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false,name = "membership_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
}
