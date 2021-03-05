package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLazyResponse {
    private Long memberId;

    private String occupation;

    private PersonRequest person;

    private MemberType memberType;

}
