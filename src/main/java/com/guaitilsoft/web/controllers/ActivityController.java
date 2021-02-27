package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityView;
import com.guaitilsoft.web.models.activity.GetActivity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/activities")
public class ActivityController {

    public static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private final ActivityService activityService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public ActivityController(ActivityService activityService,
                              ModelMapper modelMapper,
                              Utils utils) {
        this.activityService = activityService;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }

    @GetMapping
    public ResponseEntity<List<GetActivity>> get() {
        Type listType = new TypeToken<List<GetActivity>>() {
        }.getType();
        List<GetActivity> activities = modelMapper.map(activityService.list(), listType);
        activities.forEach(a -> this.utils.addUrlToMultimedia(a.getMultimedia()));
        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetActivity> getById(@PathVariable Long id) {
        GetActivity activity = modelMapper.map(activityService.get(id), GetActivity.class);
        this.utils.addUrlToMultimedia(activity.getMultimedia());
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(activity);
    }

    @GetMapping("/activities-active")
    public ResponseEntity<List<GetActivity>> getAllActivitiesActive() {
        Type listType = new TypeToken<List<GetActivity>>() {
        }.getType();
        List<GetActivity> activities = modelMapper.map(activityService.getAllActivitiesActive(), listType);
        activities.forEach(a -> this.utils.addUrlToMultimedia(a.getMultimedia()));
        return ResponseEntity.ok().body(activities);
    }

    @PostMapping
    public ResponseEntity<ActivityView> post(@RequestBody ActivityView activityRequest) {
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        logger.info("Creating activity");
        this.utils.loadMultimedia(activity.getMultimedia());
        activityService.save(activity);
        ActivityView activityResponse = modelMapper.map(activity, ActivityView.class);
        this.utils.addUrlToMultimedia(activityRequest.getMultimedia());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(activity.getId())
                .toUri();
        logger.info("Created activity : {}", activityResponse.getId());

        return ResponseEntity.created(location).body(activityResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityView> put(@PathVariable Long id, @RequestBody ActivityView activityRequest) {
        if (!id.equals(activityRequest.getId())) {
            throw new ApiRequestException("El id de la actividad: " + activityRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        this.utils.loadMultimedia(activity.getMultimedia());
        logger.info("Updating Activity with id {}", id);
        activityService.update(id, activity);
        ActivityView activityResponse = modelMapper.map(activity, ActivityView.class);
        this.utils.addUrlToMultimedia(activityRequest.getMultimedia());
        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ActivityView> delete(@PathVariable Long id) {
        ActivityView activityResponse = modelMapper.map(activityService.get(id), ActivityView.class);
        logger.info("Deleting Activity with id {}", id);
        activityService.delete(id);
        logger.info("Deleted Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ActivityView> deleteMultimediaById(@RequestParam Long id,
                                                             @RequestParam Long idMultimedia) {
        logger.info("Deleting Activity Multimedia with id {}", id);
        ActivityView activityResponse = modelMapper.map(
                activityService.deleteMultimediaById(id, idMultimedia),
                ActivityView.class);
        this.utils.addUrlToMultimedia(activityResponse.getMultimedia());
        logger.info("Deleted Activity Multimedia with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

}
