package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guaitilsoft.models.constant.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 60)
    @Size(min = 6, max = 60)
    private String occupation;

    private LocalDateTime affiliationDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @JsonManagedReference
    @OneToMany(targetEntity = Local.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<Local> locals;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        initLocals();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        initLocals();
    }

    private void initLocals() {
        for (Local local : locals)
            local.setMember(this);
    }
}

