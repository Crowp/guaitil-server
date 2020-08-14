package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String username;

    private List<Role> roles;

    private String token;

    private Person person;
}
