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
public class Kitchen implements Serializable {

    @Id
    @Column(name = "kitchen_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Local local;
}
