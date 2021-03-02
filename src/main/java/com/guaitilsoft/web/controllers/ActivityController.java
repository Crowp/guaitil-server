package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
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
    public ResponseEntity<List<ActivityResponse>> get() {
        Type listType = new TypeToken<List<ActivityResponse>>() {
        }.getType();
        List<ActivityResponse> activities = modelMapper.map(activityService.list(), listType);
        activities.forEach(a -> this.utils.addUrlToMultimedia(a.getMultimedia()));
        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("{id}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable Long id) {
        ActivityResponse activity = modelMapper.map(activityService.get(id), ActivityResponse.class);
        this.utils.addUrlToMultimedia(activity.getMultimedia());
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(activity);
    }

    @GetMapping("/activities-active")
    public ResponseEntity<List<ActivityResponse>> getAllActivitiesActive() {
        Type listType = new TypeToken<List<ActivityResponse>>() {
        }.getType();
        List<ActivityResponse> activities = modelMapper.map(activityService.getAllActivitiesActive(), listType);
        activities.forEach(a -> this.utils.addUrlToMultimedia(a.getMultimedia()));
        return ResponseEntity.ok().body(activities);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> post(@RequestBody ActivityRequest activityRequest) {
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        logger.info("Creating activity");
        this.utils.loadMultimedia(activity.getMultimedia());
        loadLocalDescriptions(activityRequest, activity);
        activityService.save(activity);
        ActivityResponse activityResponse = modelMapper.map(activity, ActivityResponse.class);
        this.utils.addUrlToMultimedia(activityRequest.getMultimedia());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(activity.getId())
                .toUri();
        logger.info("Created activity : {}", activityResponse.getId());

        return ResponseEntity.created(location).body(activityResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityResponse> put(@PathVariable Long id, @RequestBody ActivityRequest activityRequest) {
        if (!id.equals(activityRequest.getId())) {
            throw new ApiRequestException("El id de la actividad: " + activityRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        this.utils.loadMultimedia(activity.getMultimedia());
        logger.info("Updating Activity with id {}", id);
        activityService.update(id, activity);
        ActivityResponse activityResponse = modelMapper.map(activity, ActivityResponse.class);
        this.utils.addUrlToMultimedia(activityRequest.getMultimedia());
        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ActivityResponse> delete(@PathVariable Long id) {
        ActivityResponse activityResponse = modelMapper.map(activityService.get(id), ActivityResponse.class);
        logger.info("Deleting Activity with id {}", id);
        activityService.delete(id);
        logger.info("Deleted Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ActivityResponse> deleteMultimediaById(@RequestParam Long id,
                                                                @RequestParam Long idMultimedia) {
        logger.info("Deleting Activity Multimedia with id {}", id);
        ActivityResponse activityResponse = modelMapper.map(
                activityService.deleteMultimediaById(id, idMultimedia),
                ActivityResponse.class);
        this.utils.addUrlToMultimedia(activityResponse.getMultimedia());
        logger.info("Deleted Activity Multimedia with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    private void loadLocalDescriptions(@RequestBody ActivityRequest activityRequest, Activity activity) {
        activity.getLocalsDescriptions().clear();
        activityRequest.getLocals().forEach(local -> {
            LocalDescription localDescription = this.utils.loadFullLocalDescriptionByLocalId(local.getId());
            activity.getLocalsDescriptions().add(localDescription);
        });
    }
}
