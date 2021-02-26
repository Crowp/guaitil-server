package com.guaitilsoft.web.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.web.models.member.LoadMember;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GetUsers {
    private Long id;

    List<Role> roles;

    private LoadMember member;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
