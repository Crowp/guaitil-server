package com.guaitilsoft.web.models.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.localDescription.LocalDescriptionResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ActivityResponse {

    private Long id;

    private Boolean isActive;

    private ActivityDescription activityDescription;

    private List<LocalDescriptionResponse> localDescriptions;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    private List<MultimediaResponse> multimedia;

    public void removeMultimediaById(Long id){
        this.multimedia.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .ifPresent(media -> this.multimedia.remove(media));
    }
}
