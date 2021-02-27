package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.local.LoadLocal;
import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberRequest {
    private Long memberId;

    private String occupation;

    private PersonRequest person;

    private MemberType memberType;

    private List<LoadLocal> locals;

}
