package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity

public class Experience {
    @Id
    @Column
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
