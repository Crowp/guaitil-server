package com.guaitilsoft.services.activity;

import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;

import java.util.List;

public interface ActivityService {
    List<ActivityResponse> list();

    ActivityResponse get(Long id);

    ActivityResponse save(ActivityRequest entity);

    ActivityResponse update(Long id, ActivityRequest entity);

    ActivityResponse updateIsActive(Long id);

    List<ActivityResponse> getAllActivitiesActive();

    ActivityResponse deleteMultimediaById(Long id, Long idMedia);

    void delete(Long id);

}
