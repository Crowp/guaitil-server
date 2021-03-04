package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityLazyResponse {
    private Long id;

    private ActivityDescriptionRequest activityDescriptionRequest;

    private Boolean isActive;

}
