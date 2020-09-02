package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.activity.ActivityView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/activity")
public class ActivityController {

    public static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private ActivityService activityService;
    private MultimediaService multimediaService;
    private LocalService localService;
    private ModelMapper modelMapper;

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

    @GetMapping
    public ResponseEntity<List<ActivityView>> get(){
        Type listType  = new TypeToken<List<ActivityView>>(){}.getType();
        List<ActivityView> activities = modelMapper.map(activityService.list(),listType);
        return  ResponseEntity.ok().body(activities);
    }

    @GetMapping("{id}")
    public ResponseEntity<ActivityView> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        ActivityView activity = modelMapper.map(activityService.get(id),ActivityView.class);
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(activity);
    }

    @PostMapping
    public ResponseEntity<ActivityView> post(@RequestBody ActivityView activityRequest) throws Exception, EntityNotFoundException{
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        logger.info("Creating activity");
        if(activity.getMultimedia().size() > 0){
            List<Multimedia> multimediaList = new ArrayList<>();
            activity.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            activity.setMultimedia(multimediaList);
        }
        if(activity.getLocals().size() > 0){
            List<Local> localList = new ArrayList<>();
            activity.getLocals().forEach(item -> {
                Local local = localService.get(item.getId());
                localList.add(local);
            });
            activity.setLocals(localList);
        }
        activityService.save(activity);
        ActivityView activityResponse = modelMapper.map(activity, ActivityView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(activity.getId())
                .toUri();
        logger.info("Created activity : {}", activityResponse.getId());

        return ResponseEntity.created(location).body(activityResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityView> put(@PathVariable Long id, @RequestBody ActivityView activityRequest) throws Exception, EntityNotFoundException {
        if(!id.equals(activityRequest.getId())){
            throw new ApiRequestException("El id de la actividad: " + activityRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Activity activity = modelMapper.map(activityRequest, Activity.class);
        logger.info("Updating Activity with id {}", id);
        activityService.update(id, activity);
        ActivityView activityResponse = modelMapper.map(activity,ActivityView.class);
        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ActivityView> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        ActivityView activityResponse = modelMapper.map(activityService.get(id), ActivityView.class);
        logger.info("Deleting Activity with id {}", id);
        activityService.delete(id);
        logger.info("Deleted Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }
}
