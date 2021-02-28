package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.guaitilsoft.models.constant.LocalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    LocalDescription localDescription;

    @JsonBackReference
    @JoinColumn(name = "memberId")
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "local",
            orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Multimedia> multimedia = new ArrayList<>();

    private Boolean state = true;

    public String getPersonId() {
        return member.getDni();
    }

    public LocalType getLocalType() {
        return this.localDescription.getLocalType();
    }

    public Long getMemberId() {
        return this.member.getMemberId();
    }

    public void removeMultimediaById(Multimedia multimedia) {
        this.multimedia.remove(multimedia);
    }

    @PrePersist
    public void prePersist() {
        this.state = true;
    }

}
