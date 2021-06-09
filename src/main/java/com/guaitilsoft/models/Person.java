package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private String id;

    @NotEmpty
    @Column(length = 40)
    @Size(min = 3, max = 40)
    private String name;

    @NotEmpty
    @Column(length = 40)
    @Size(min = 3, max = 60)
    private String firstLastName;

    @Column(length = 40)
    @Size(max = 60)
    private String secondLastName;

    @NotEmpty
    @Column(length = 8)
    @Size(min = 8, max = 8)
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    @Size(min = 13, max = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
