package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String password;

    @NonNull
    @Column(nullable = false, name = "first_login")
    private Boolean firstLogin;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    public String getEmail(){
        return person.getEmail();
    }
}
