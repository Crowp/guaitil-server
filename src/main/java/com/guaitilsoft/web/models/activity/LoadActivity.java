package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.local.LoadLocal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LoadActivity {
    private Long id;

    private String name;

    private String description;

    private Date activityDate;

    private ActivityType activityType;

    private Date createdAt;

    private Date updatedAt;

    private Address address;
}
