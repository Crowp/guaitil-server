package com.guaitilsoft.web.models.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReport {

    private String id;

    private String name;

    private String firstLastName;

    private String SecondLastName;

    private String occupation;

    private String telephone;
}
