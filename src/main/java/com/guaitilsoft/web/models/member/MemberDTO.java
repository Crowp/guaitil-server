package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class MemberDTO {
    private Long id;
}
