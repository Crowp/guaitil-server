package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.LocalType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "local")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    public String personId(){
        return member.getPersonId();
    }
}
