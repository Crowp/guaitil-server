package com.guaitilsoft.web.models.user;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.MemberLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReportResponse {
    private Long id;

    private String role;

    private MemberLazyResponse member;

    public void setRoles(Role rol) {

    }
}
