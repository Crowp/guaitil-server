package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.person.PersonView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoadMember {
    private Long memberId;

    private String occupation;

    private PersonView person;

    private MemberType memberType;
}
