package com.guaitilsoft.web.models;

import com.guaitilsoft.models.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String email;

    private String password;

    private String idPerson;

    List<Role> roles;
}
