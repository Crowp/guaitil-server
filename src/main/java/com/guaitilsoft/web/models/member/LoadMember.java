package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LoadMember {
    private Long id;

    private String occupation;

    private Date createdAt;

    private Date updatedAt;

    private Person person;

    private MemberType memberType;
}
