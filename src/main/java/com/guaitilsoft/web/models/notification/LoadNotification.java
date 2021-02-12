package com.guaitilsoft.web.models.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LoadNotification {
    private Long id;

    private String description;

    private Boolean isActive;

    private Date date;
}
