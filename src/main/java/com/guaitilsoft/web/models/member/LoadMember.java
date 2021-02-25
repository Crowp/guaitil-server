package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoadMember {
    private Long id;

    private String occupation;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Person person;

    private MemberType memberType;
}
