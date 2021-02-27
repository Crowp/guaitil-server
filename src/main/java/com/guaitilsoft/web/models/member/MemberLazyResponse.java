package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberLazyResponse {
    private Long memberId;

    private String occupation;

    private PersonRequest person;

    private MemberType memberType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
