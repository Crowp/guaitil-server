package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.MultimediaType;
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
    private String fileName;

    @Enumerated(EnumType.STRING)
    private MultimediaType type;

    private String format;

}
