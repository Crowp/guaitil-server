package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Quotation implements Serializable {

    @Id
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false,name = "amond_person")
    private String amountPerson;

    @Column(nullable = false,name = "date_quotation")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateQuotation;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
}
