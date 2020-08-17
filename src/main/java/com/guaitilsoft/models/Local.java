package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guaitilsoft.models.constant.LocalType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "local")
@AllArgsConstructor
@NoArgsConstructor
public class Local {

    @Id
    @Column(name = "_id")
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

<<<<<<< HEAD
=======
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="address_id")
    private Address address;

>>>>>>> f32be5cf6e94aca8bdcd913504aefae54274370b
    @JsonIgnoreProperties("locals")
    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;
}
