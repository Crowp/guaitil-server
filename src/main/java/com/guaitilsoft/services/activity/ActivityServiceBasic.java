package com.guaitilsoft.services.activity;

import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;

import java.util.List;

public interface ActivityServiceBasic {
    List<ActivityResponse> list();

    ActivityResponse get(Long id);

    ActivityResponse save(ActivityRequest entity);

    ActivityResponse update(Long id, ActivityRequest entity);

    void delete(Long id);

}
