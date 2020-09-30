package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.activity.ActivityView;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
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
@RequestMapping(path = "/api/activity")
public class ActivityController {

    public static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private final ActivityService activityService;
    private final MultimediaService multimediaService;
    private final LocalService localService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActivityController(
            ActivityService activityService,
            MultimediaService multimediaService,
            LocalService localService,
            ModelMapper modelMapper){
        this.activityService  = activityService;
        this.multimediaService = multimediaService;
        this.localService = localService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/actividades")
    public ResponseEntity<List<ActivityView>> get(){
        Type listType  = new TypeToken<List<ActivityView>>(){}.getType();
        List<ActivityView> activities = modelMapper.map(activityService.list(),listType);
        activities.forEach(this::addUrlToMultimedia);
        return  ResponseEntity.ok().body(activities);
    }

    @GetMapping("{id}")
    public ResponseEntity<ActivityView> getById(@PathVariable Long id) {
        ActivityView activity = modelMapper.map(activityService.get(id),ActivityView.class);
        addUrlToMultimedia(activity);
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(activity);
    }

    @PostMapping
    public ResponseEntity<ActivityView> post(@RequestBody ActivityView activityRequest) {
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        logger.info("Creating activity");
        loadMultimedia(activity);
        loadLocals(activity);
        activityService.save(activity);
        ActivityView activityResponse = modelMapper.map(activity, ActivityView.class);
        addUrlToMultimedia(activityRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(activity.getId())
                .toUri();
        logger.info("Created activity : {}", activityResponse.getId());

        return ResponseEntity.created(location).body(activityResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityView> put(@PathVariable Long id, @RequestBody ActivityView activityRequest) {
        if(!id.equals(activityRequest.getId())){
            throw new ApiRequestException("El id de la actividad: " + activityRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        loadMultimedia(activity);
        loadLocals(activity);
        logger.info("Updating Activity with id {}", id);
        activityService.update(id, activity);
        ActivityView activityResponse = modelMapper.map(activity,ActivityView.class);
        addUrlToMultimedia(activityRequest);
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

    @DeleteMapping("delete-multimedia-by-id")
    public ResponseEntity<ActivityView> deleteMultimediaById(@RequestParam Long id,
                                                             @RequestParam Long idMultimedia) {
        logger.info("Deleting Activity Multimedia with id {}", id);
        ActivityView activityResponse = modelMapper.map(
                activityService.deleteMultimediaById(id, idMultimedia),
                ActivityView.class);
        addUrlToMultimedia(activityResponse);
        logger.info("Deleted Activity Multimedia with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    private void addUrlToMultimedia(ActivityView activityView) {
        activityView.getMultimedia().forEach(m -> {
            String url = getUrlHost(m);
            m.setUrl(url);
        });
    }

    private String getUrlHost(MultimediaResponse multimediaResponse) {
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }

    private void loadMultimedia(Activity activity) {
        if (activity.getMultimedia().size() > 0) {
            List<Multimedia> multimediaList = new ArrayList<>();
            activity.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            activity.setMultimedia(multimediaList);
        }
    }

    private void loadLocals(Activity activity) {
        if (activity.getLocals().size() > 0) {
            List<Local> localList = new ArrayList<>();
            activity.getLocals().forEach(item -> {
                Local local = localService.get(item.getId());
                localList.add(local);
            });
            activity.setLocals(localList);
        }
    }
}
