package com.guaitilsoft.web.models.member;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.web.models.local.LoadLocal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberView {
    private Long id;

    private String occupation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Person person;

    private MemberType memberType;

    private List<LoadLocal> locals;
}
