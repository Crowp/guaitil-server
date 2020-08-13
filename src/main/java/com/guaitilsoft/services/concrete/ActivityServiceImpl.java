package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.repositories.ActivityRepository;
import com.guaitilsoft.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> list() {
        Iterable<Activity> iterable = activityRepository.findAll();
        List<Activity> activities = new ArrayList<>();
        iterable.forEach(activities::add);
        return activities;
    }

    @Override
    public Activity get(Long id) {
        Activity activity = activityRepository.findById(id).orElse(null);
        if(activity == null){
            throw new EntityNotFoundException();
        }
        return activity;
    }

    @Override
    public void save(Activity entity) {
        activityRepository.save(entity);
    }

    @Override
    public void update(Long id, Activity entity) {
        Activity activity = this.get(id);
        activity.setName(entity.getName());
        activity.setDescription(entity.getDescription());
        activity.setActivityDate(entity.getActivityDate());
        activity.setAddress(entity.getAddress());
        activity.setMultimedia(entity.getMultimedia());
        activityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Activity activity = this.get(id);
        if(activity != null){
            activityRepository.delete(activity);
        }
    }
}
