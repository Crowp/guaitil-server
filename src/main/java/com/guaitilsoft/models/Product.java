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

    @Column
    private Boolean status;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private ProductDescription productDescription;

    @ManyToOne(targetEntity = Local.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "localId")
    private Local local;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void removeMultimediaById(Multimedia multimedia) {
        this.multimedia.remove(multimedia);
    }

    @PrePersist
    public void prePersist(){
        this.status =  true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
