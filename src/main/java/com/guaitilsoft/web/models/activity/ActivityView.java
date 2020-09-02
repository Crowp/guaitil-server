package com.guaitilsoft.web.models.Activity;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.local.GetLocal;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ActivityView {
    private Long id;

    private String name;

    private String description;

    private Date activityDate;

    private ActivityType activityType;

    private Address address;

    private Date createdAt;

    private Date updatedAt;

    private List<GetLocal> locals;

    private List<MultimediaResponse> multimedia;
}
