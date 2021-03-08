package com.guaitilsoft.models;

import lombok.*;

import javax.persistence.*;
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
    private Boolean showProduct;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private ProductDescription productDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "localId")
    private Local local;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    public void removeMultimediaById(Multimedia multimedia) {
        this.multimedia.remove(multimedia);
    }

    @PrePersist
    public void prePersist(){
        this.showProduct =  true;
    }
}
