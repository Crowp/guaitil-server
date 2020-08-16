package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.LocalType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "local")
@AllArgsConstructor
@NoArgsConstructor
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String telephone;

    @Enumerated(EnumType.STRING)
    private LocalType localType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="addressid")
    private Address address;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="personid")
    private Person person;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;
}
