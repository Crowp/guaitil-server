package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Local implements Serializable {

    @Id
    @Column(name = "local_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long telephone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(nullable = false, name = "local_type")
    @Enumerated(EnumType.STRING)
    private LocalType localType;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> multimedia;
}
