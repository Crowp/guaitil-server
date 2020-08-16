package com.guaitilsoft.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "multimedia")
@AllArgsConstructor
@NoArgsConstructor
public class Multimedia {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String url;

    public Multimedia(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
