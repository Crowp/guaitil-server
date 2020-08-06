package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lodging implements Serializable {

    @Id
    @Column
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Local local;
}
