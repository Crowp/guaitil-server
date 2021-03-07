package com.guaitilsoft.web.models.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.person.PersonDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberRequest {
    private Long id;

    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime affiliationDate;

    private PersonDTO person;

    private MemberType memberType;

    private List<LocalRequest> locals;
}
