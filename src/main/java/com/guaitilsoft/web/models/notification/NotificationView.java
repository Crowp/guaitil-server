package com.guaitilsoft.web.models.notification;

import com.guaitilsoft.web.models.member.LoadMember;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class NotificationView {

    private Long id;

    private String description;

    private Boolean isActive;

    private Date date;

    private List<LoadMember> members;
}
