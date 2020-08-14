package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name="associated")
@AllArgsConstructor
@NoArgsConstructor
public class Associated {

    @Id
    @Column(name="associatedId", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="personid")
    private Person person;
}

