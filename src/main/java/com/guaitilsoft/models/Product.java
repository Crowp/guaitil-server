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
    private Boolean show;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private ProductDescription productDescription;

    @ManyToOne(targetEntity = Local.class)
    @JoinColumn(name = "localId")
    private Local local;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    public void removeMultimediaById(Multimedia multimedia) {
        this.multimedia.remove(multimedia);
    }

    @PrePersist
    public void prePersist(){
        this.show =  true;
    }
}
