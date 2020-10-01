package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.ActivityType;
import com.guaitilsoft.repositories.ActivityRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImp implements ActivityService {

    private final ActivityRepository activityRepository;
    private final MultimediaService multimediaService;
    private final TourService tourService;

    @Autowired
    public ActivityServiceImp(ActivityRepository activityRepository, MultimediaService multimediaService, TourService tourService) {
        this.activityRepository = activityRepository;
        this.multimediaService = multimediaService;
        this.tourService = tourService;
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
        assert id != null;

        Activity activity = activityRepository.findById(id).orElse(null);
        if(activity != null){
            return activity;
        }
        throw new EntityNotFoundException("No se encontro una actividad con el id: " + id);
    }

    @Override
    public void save(Activity entity) {
        assert entity != null;
        entity.setUpdatedAt(new Date());
        entity.setCreatedAt(new Date());
        activityRepository.save(entity);
    }

    @Override
    public void update(Long id, Activity entity) {
        assert id != null;
        assert entity != null;

        Activity activity = this.get(id);
        activity.setName(entity.getName());
        activity.setUpdatedAt(new Date());
        activity.setDescription(entity.getDescription());
        activity.setActivityDate(entity.getActivityDate());
        activity.setActivityType(entity.getActivityType());
        activity.setUpdatedAt(entity.getUpdatedAt());
        activity.setAddress(entity.getAddress());
        activity.setMultimedia(entity.getMultimedia());

        if(entity.getActivityType() == ActivityType.EXPERIENCE){
            tourService.deleteTourByActivityId(entity.getId());
        }
        activityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Activity activity = this.get(id);
        List<Multimedia> activityList = new ArrayList<>(activity.getMultimedia());
        activity.setMultimedia(null);
        activityRepository.save(activity);
        if(activityList.size() > 0){
            activityList.forEach(media -> multimediaService.delete(media.getId()));
        }
        tourService.deleteTourByActivityId(id);
        activityRepository.delete(activity);
    }

    @Override
    public Activity deleteMultimediaById(Long id, Long idMultimedia) {
        Activity activity = this.get(id);
        List<Multimedia> multimedia = activity.getMultimedia()
                .stream()
                .filter(media -> !media.getId().equals(idMultimedia))
                .collect(Collectors.toList());
        activity.setMultimedia(multimedia);
        activityRepository.save(activity);
        multimediaService.delete(idMultimedia);
        return activity;
    }

    @Override
    public void removeLocalFromActivity(Long localId) {
        List<Activity> activityList = this.list();
        activityList.forEach(activity -> {
            List<Local> localList = activity.getLocals()
                    .stream()
                    .filter(local -> !local.getId().equals(localId))
                    .collect(Collectors.toList());
            if (!localList.isEmpty()){
                activity.setLocals(localList);
                this.update(activity.getId(), activity);
            }else {
                this.delete(activity.getId());
            }
        });
    }
}
