package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guaitilsoft.models.constant.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    private String occupation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Person person;

    @OneToMany(
            targetEntity = Local.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "member"
    )
    @JsonManagedReference
    private List<Local> locals;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public String getEmail() {
        return person.getEmail();
    }

    public String getPersonId() {
        return person.getId();
    }

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

