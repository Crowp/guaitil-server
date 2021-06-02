package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 60)
    @Size(min = 3, max = 60)
    private String name;

    @Lob
    @NotEmpty
    @Column(length = 1000)
    @Size(min = 100, max = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductPrice productPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
