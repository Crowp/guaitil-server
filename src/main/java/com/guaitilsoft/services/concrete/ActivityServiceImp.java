package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.*;
import com.guaitilsoft.repositories.ActivityRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guaitilsoft.models.constant.NotificationMessage.ACTIVITY_NOTIFICATION;

@Service
public class ActivityServiceImp implements ActivityService {

    private final ActivityRepository activityRepository;
    private final MultimediaService multimediaService;
    private final NotificationService notificationService;

    @Autowired
    public ActivityServiceImp(ActivityRepository activityRepository, MultimediaService multimediaService, NotificationService notificationService) {
        this.activityRepository = activityRepository;
        this.multimediaService = multimediaService;
        this.notificationService = notificationService;
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
        throw new EntityNotFoundException("No se encontró una actividad con el id: " + id);
    }

    @Override
    public void save(Activity entity) {
        assert entity != null;
        activityRepository.save(entity);

        notificationService.save(ACTIVITY_NOTIFICATION.getMessage() + entity.getName(), memberList(entity));
    }

    @Override
    public void update(Long id, Activity entity) {
        assert id != null;
        assert entity != null;

        Activity activity = this.get(id);
        activity.setName(entity.getName());
        activity.setDescription(entity.getDescription());
        activity.setActivityDate(entity.getActivityDate());
        activity.setActivityType(entity.getActivityType());
        activity.setPersonCost(entity.getPersonCost());
        activity.setUpdatedAt(entity.getUpdatedAt());
        activity.setAddress(entity.getAddress());
        activity.setIsActive(entity.getIsActive());
        activity.setMultimedia(entity.getMultimedia());
        activity.setLocalsDescriptions(entity.getLocalsDescriptions());

        activityRepository.save(activity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Activity activity = this.get(id);
        List<Multimedia> multimedia = activity.getMultimedia();
        activity.setMultimedia(null);
        activityRepository.save(activity);
        if(multimedia.size() > 0){
            multimedia.forEach(media -> multimediaService.delete(media.getId()));
        }

        activityRepository.delete(activity);
    }

    @Override
    public Activity deleteMultimediaById(Long id, Long idMultimedia) {
        activityRepository.deleteMultimediaById(idMultimedia);
        return this.get(id);
    }

    @Override
    public List<Activity> getAllActivitiesActive() {
        return this.list().stream().filter(Activity::getIsActive).collect(Collectors.toList());
    }

    private List<Member> memberList(Activity activity){
        Set<Local> activityLocals = activity.getLocalsDescriptions().stream().map(LocalDescription::getLocal).collect(Collectors.toSet());
        return activityLocals.stream().map(Local::getMember).collect(Collectors.toList());
    }

}
