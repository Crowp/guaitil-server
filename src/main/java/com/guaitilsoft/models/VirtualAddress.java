package com.guaitilsoft.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "virtual_Address")
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String latitude;

    @NotEmpty
    private  String longitude;
}
