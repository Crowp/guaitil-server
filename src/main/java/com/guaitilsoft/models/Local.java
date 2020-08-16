package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guaitilsoft.models.constant.LocalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @JoinColumn(name="address_id")
    private Address address;

    @JsonIgnoreProperties("locals")
    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;
}
