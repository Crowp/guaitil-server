package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.local.LoadLocal;
import com.guaitilsoft.web.models.person.PersonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberView {
    private Long id;

    private String occupation;

    private PersonView person;

    private MemberType memberType;

    private List<LoadLocal> locals;
}
