package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.Member;
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

    private List<Role> roles;

    private String token;

    private Member member;
}
