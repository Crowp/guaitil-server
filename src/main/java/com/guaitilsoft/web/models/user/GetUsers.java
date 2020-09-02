package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.LoadMember;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetUsers {
    private Long id;

    List<Role> roles;

    private LoadMember member;
}
