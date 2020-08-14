package com.guaitilsoft.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "virtual_Address")
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAddress {
    @Id
    private Long id;

    @NotEmpty
    private String latitude;

    @NotEmpty
    private  String longitude;
}
