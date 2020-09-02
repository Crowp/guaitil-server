package com.guaitilsoft.services;

import com.guaitilsoft.models.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> list();

    Activity get(Long id);

    void save(Activity entity);

    void update(Long id, Activity entity);

    void delete(Long id);

    void deleteMultimediaById(Long id, Long idMultimedia);
}
