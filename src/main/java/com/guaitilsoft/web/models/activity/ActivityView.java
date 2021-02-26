package com.guaitilsoft.web.models.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.local.LoadLocal;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ActivityView {
    private Long id;

    private String name;

    private String description;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityDate;

    private ActivityType activityType;

    private Address address;

    private Double personCost;

    private List<LoadLocal> locals;

    private List<MultimediaResponse> multimedia;
}
