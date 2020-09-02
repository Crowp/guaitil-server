package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.local.LoadLocal;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberView {
    private Long id;

    private String occupation;

    private Date createdAt;

    private Date updatedAt;

    private Person person;

    private MemberType memberType;

    private List<LoadLocal> locals;
}
