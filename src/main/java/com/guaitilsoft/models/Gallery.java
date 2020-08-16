package com.guaitilsoft.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "gallery")
@AllArgsConstructor
@NoArgsConstructor
public class Gallery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> multimedia;
}
