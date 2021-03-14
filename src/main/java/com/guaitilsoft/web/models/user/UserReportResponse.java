package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.MemberLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserReportResponse {
    private Long id;

    private String role;

    private MemberLazyResponse member;

    public void setRoles(List<Role> roles) {
        List<String> listRoles = roles.stream().map(Role::getMessage).collect(Collectors.toList());
        this.role = String.join("|", listRoles);
    }
}
