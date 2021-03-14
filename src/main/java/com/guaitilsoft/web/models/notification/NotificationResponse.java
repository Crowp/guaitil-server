package com.guaitilsoft.web.models.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.web.models.member.MemberLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class NotificationResponse {

    private Long id;

    private String description;

    private Boolean isActive;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private List<MemberLazyResponse> members;
}
