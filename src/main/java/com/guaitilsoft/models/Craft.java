package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Craft {

    @Id
    @Column
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Workshop> workshops;

    @OneToOne(fetch = FetchType.EAGER)
    private Product products;
}
