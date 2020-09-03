package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String password;

    @NotBlank
    private Member member;

    List<Role> roles;
}
