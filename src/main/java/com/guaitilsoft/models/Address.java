package com.guaitilsoft.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String physicalAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private VirtualAddress virtualAddress;
}
