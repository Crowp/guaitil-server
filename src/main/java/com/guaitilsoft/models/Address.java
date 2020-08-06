package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @Column
    private Long id;

    @Column(nullable = false,name = "physical_address")
    private String physicalAddress;

    @Column(nullable = false,name = "virtual_address")
    private String virtualAddress;
}
