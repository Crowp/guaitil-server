package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberLazyResponse {
    private Long id;

    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime affiliationDate;

    private PersonRequest person;

    private MemberType memberType;

}
