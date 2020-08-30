package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="local_id")
    private Local local;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_priceid")
    private ProductPrice productPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;
}
