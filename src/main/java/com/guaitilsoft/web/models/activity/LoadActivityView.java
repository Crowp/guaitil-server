package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class LoadActivityView {
    private Long id;

    private String name;

    private String description;

    private Date activityDate;

    private ActivityType activityType;

    private Date createdAt;

    private Date updatedAt;

    private Address address;
}
