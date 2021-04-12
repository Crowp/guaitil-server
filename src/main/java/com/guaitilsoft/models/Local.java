package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.guaitilsoft.models.constant.LocalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    LocalDescription localDescription;

    @JsonBackReference
    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "local")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    private Boolean showLocal = true;

    public LocalType getLocalType() {
        return this.localDescription.getLocalType();
    }

    public void removeMultimediaById(Multimedia multimedia) {
        this.multimedia.remove(multimedia);
    }

    public String getFullMemberName(){
        return this.member.getPerson().getName()+" "+this.member.getPerson().getFirstLastName()+" "+this.member.getPerson().getSecondLastName();
    }
}
