package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ProductType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(targetEntity = Local.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "localId")
    private Local local;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductPrice productPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;
}
