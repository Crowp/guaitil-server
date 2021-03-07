package com.guaitilsoft.web.models.activity;

import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.web.models.activityDescription.ActivityDescriptionResponse;
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

    private ActivityDescriptionResponse activityDescription;

    private List<LocalDescriptionResponse> localsDescriptions;

    private List<MultimediaResponse> multimedia;

    public void removeMultimediaById(Long id){
        this.multimedia.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .ifPresent(media -> this.multimedia.remove(media));
    }
}
