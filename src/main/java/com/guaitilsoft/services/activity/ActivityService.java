package com.guaitilsoft.services.activity;

import com.guaitilsoft.web.models.activity.ActivityResponse;

import java.util.List;

public interface ActivityService extends ActivityServiceBasic {
    ActivityResponse deleteMultimediaById(Long id, Long idMedia);
    List<ActivityResponse> getAllActivitiesActive();
}
