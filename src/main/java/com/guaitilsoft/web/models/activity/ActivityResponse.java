package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.web.models.localDescription.LocalDescriptionResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ActivityResponse {

    private Long id;

    private Boolean isActive;

    private ActivityDescription activityDescription;

    private List<LocalDescriptionResponse> localDescriptions;

    private List<MultimediaResponse> multimedia;
}
