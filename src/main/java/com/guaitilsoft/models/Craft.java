package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Craft implements Serializable {

    @Id
    @Column
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Workshop> workshops;

    @OneToOne(fetch = FetchType.EAGER)
    private Product products;
}
