package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_login")
    private Boolean firstLogin;

    @Column(nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
}
