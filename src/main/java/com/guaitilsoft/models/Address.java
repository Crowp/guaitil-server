package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @Column
    private Long id;

    @Column(nullable = false,name = "physical_address")
    private String physicalAddress;

    @Column(nullable = false,name = "virtual_address")
    private String virtualAddress;

}
