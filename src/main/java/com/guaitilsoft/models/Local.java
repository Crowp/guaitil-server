package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.LocalType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
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

    @OneToOne
    private Address address;

    @OneToOne
    private Person person;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Multimedia> multimedia;
}
