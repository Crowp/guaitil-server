package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Role;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String password;

    private Boolean firstLogin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Member member;

    public String getEmail(){
        return member.getPerson().getEmail();
    }

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
