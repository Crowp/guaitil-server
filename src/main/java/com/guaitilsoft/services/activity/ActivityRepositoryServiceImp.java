package com.guaitilsoft.services.activity;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ActivityRepositoryServiceBasic")
public class ActivityRepositoryServiceImp implements ActivityRepositoryService {

    private final ActivityRepository activityRepository;


    @Autowired
    public ActivityRepositoryServiceImp(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> list() {
        return getActivities();
    }

    private List<Activity> getActivities() {
        Iterable<Activity> iterable = this.activityRepository.findAll();
        List<Activity> activities = new ArrayList<>();
        iterable.forEach(activities::add);
        return activities;
    }

    @Override
    public Activity get(Long id) {
        return this.activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity save(Activity activity) {
        assert activity != null;
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Long id, Activity entity) {
        assert id != null;
        assert entity != null;

        Activity activity = this.get(id);
        activity.setActivityDescription(entity.getActivityDescription());
        activity.setIsActive(entity.getIsActive());
        activity.setMultimedia(entity.getMultimedia());
        activity.setLocalsDescriptions(entity.getLocalsDescriptions());

        return activityRepository.save(activity);
    }

    @Override
    public void delete(Long id) {
        Activity activity = this.get(id);
        this.activityRepository.delete(activity);
    }
}
