package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.ActivityRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.TourService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImp implements ActivityService {

    private ActivityRepository activityRepository;
    private MultimediaService multimediaService;
    private TourService tourService;

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
        Instant nowGmt = Instant.now();
        DateTimeZone americaCostaRica = DateTimeZone.forID("America/Costa_Rica");
        DateTime nowCostaRica = nowGmt.toDateTime(americaCostaRica);
        Date today = nowCostaRica.toDate();

        Activity activity = this.get(id);
        activity.setName(entity.getName());
        activity.setDescription(entity.getDescription());
        activity.setActivityDate(entity.getActivityDate());
        activity.setActivityType(entity.getActivityType());
        activity.setUpdatedAt(today);
        activity.setAddress(entity.getAddress());
        activity.setMultimedia(entity.getMultimedia());

        activityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Activity activity = this.get(id);
        if(activity.getMultimedia().size() > 0){
            activity.getMultimedia().forEach(media -> {
                multimediaService.delete(media.getId());
            });
        }
        tourService.deleteTourByActivityId(id);
        activityRepository.delete(activity);
    }

    @Override
    public void deleteMultimediaById(Long id, Long idMultimedia) {
        Activity activity = this.get(id);
        List<Multimedia> multimedia = activity.getMultimedia()
                .stream()
                .filter(media -> !media.getId().equals(idMultimedia))
                .collect(Collectors.toList());
        activity.setMultimedia(multimedia);
        activityRepository.save(activity);
        multimediaService.delete(idMultimedia);
    }
}
