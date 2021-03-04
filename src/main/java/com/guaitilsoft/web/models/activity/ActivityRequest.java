package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionRequest;
import com.guaitilsoft.web.models.localDescription.LocalDescriptionId;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ActivityRequest {
    private Long id;

    private ActivityDescriptionRequest activityDescription;

    private List<LocalDescriptionId> locals;

    private List<MultimediaResponse> multimedia;

    private Boolean isActive;
}
