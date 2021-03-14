package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityLazyResponse {
    private Long id;

    private ActivityDescriptionResponse activityDescription;

    private Boolean isActive;

}
