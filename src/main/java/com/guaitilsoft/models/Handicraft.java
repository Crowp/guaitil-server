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
public class Handicraft implements Serializable {

    @Id
    @Column(name = "handicraft_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Workshop workshop;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
}
