package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ProductType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(targetEntity = Local.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "localId")
    private Local local;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductPrice productPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    public void removeMultimediaById(Multimedia multimedia){
        this.multimedia.remove(multimedia);
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
