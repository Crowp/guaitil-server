package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.person.PersonRequest;
import com.guaitilsoft.web.models.person.PersonResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberLazyResponse {
    private Long memberId;

    private String occupation;

    private PersonResponse person;

    private MemberType memberType;

}
