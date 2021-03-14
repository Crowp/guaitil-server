package com.guaitilsoft.services.activityDescription;

import com.guaitilsoft.models.ActivityDescription;

import java.util.List;

public interface ActivityDesRepositoryService {
    ActivityDescription get(Long id);
    List<ActivityDescription> getActivityDescriptionNoRelationships();
    void delete(Long id);
}
