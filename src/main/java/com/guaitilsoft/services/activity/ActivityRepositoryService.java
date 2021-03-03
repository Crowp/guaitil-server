package com.guaitilsoft.services.activity;

import com.guaitilsoft.models.Activity;

import java.util.List;

public interface ActivityRepositoryService {
    List<Activity> list();

    Activity get(Long id);

    Activity save(Activity entity);

    Activity update(Long id, Activity entity);

    void delete(Long id);

}
