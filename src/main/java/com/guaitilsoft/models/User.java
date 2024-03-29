package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Role;
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

    private Boolean resetPassword;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Member member;

    public String getEmail() {
        return member.getPerson().getEmail();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.resetPassword = false;
        this.firstLogin = !this.roles.contains(Role.ROLE_SUPER_ADMIN);
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
