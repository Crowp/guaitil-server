package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.MemberResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserTokenResponse {
    private Long id;

    private Boolean firstLogin;

    private Boolean resetPassword;

    private List<Role> roles;
}
