package com.guaitilsoft.web.models.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.local.LocalId;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class ActivityRequest {
    private Long id;

    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime activityDate;

    private ActivityType activityType;

    private Address address;

    private Double personCost;

    private List<LocalId> locals;

    private List<MultimediaResponse> multimedia;
}
