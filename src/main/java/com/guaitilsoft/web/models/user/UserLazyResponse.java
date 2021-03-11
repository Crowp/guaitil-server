package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.MemberLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserLazyResponse {
    private Long id;

    List<Role> roles;

    private MemberLazyResponse member;
}
