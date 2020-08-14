package com.guaitilsoft.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String physicalAddress;

    @OneToOne
    private VirtualAddress virtualAddress;


}
