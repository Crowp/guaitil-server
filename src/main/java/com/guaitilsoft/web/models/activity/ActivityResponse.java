package com.guaitilsoft.web.models.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ActivityResponse {

    private Long id;

    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime activityDate;

    private ActivityType activityType;

    private Address address;

    private Boolean isActive;

    private List<LocalDescription> localDescriptions;

    private List<MultimediaResponse> multimedia;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
