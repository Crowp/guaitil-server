package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LoadMemberDTO {
    private Long id;

    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    private Date updatedAt;

    private Person person;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;
}
