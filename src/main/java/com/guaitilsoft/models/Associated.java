package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Associated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
}
