package com.guaitilsoft.web.models.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoadActivityView {
    private Long id;

    private String name;

    private String description;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityDate;

    private Double personCost;

    private ActivityType activityType;

    private Address address;

    private Boolean isActive;
}
