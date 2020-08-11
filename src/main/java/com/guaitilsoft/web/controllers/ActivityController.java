package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.services.concrete.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/activity")
public class ActivityController {
    public static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService){this.activityService =activityService;}

    @GetMapping
    public ResponseEntity<List<Activity>> get(){
        return  ResponseEntity.ok().body(activityService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Activity> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(activityService.get(id));
    }

    @PostMapping
    public ResponseEntity<Activity> post(@RequestBody Activity activity) throws  Exception{
        logger.info("Creating activity", activity);
        activityService.save(activity);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(activity.getId())
                .toUri();
        logger.info("Created activity : {}", activity);

        return ResponseEntity.created(location).body(activity);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestBody Activity activityRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Activity with id {}", id);
        activityService.update(id, activityRequest);
        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Activity with id {}", id);
        activityService.delete(id);
        logger.info("Deleted Activity with id {}", id);
        return ResponseEntity.ok().build();
    }
}
