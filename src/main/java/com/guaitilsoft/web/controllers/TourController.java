package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Tour;
import com.guaitilsoft.services.TourService;
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
@RequestMapping(path = "/api/tour")
public class TourController {
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private TourService tourService;


    @Autowired
    public TourController(TourService tourService){this.tourService =tourService;}

    @GetMapping
    public ResponseEntity<List<Tour>> get(){
        return  ResponseEntity.ok().body(tourService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Tour> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Tour with id {}", id);
        return ResponseEntity.ok().body(tourService.get(id));
    }

    @PostMapping
    public ResponseEntity<Tour> post(@RequestBody Tour tour) throws  Exception{
        logger.info("Creating tour", tour);
        tourService.save(tour);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tour.getId())
                .toUri();
        logger.info("Created tour : {}", tour);

        return ResponseEntity.created(location).body(tour);
    }
    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestBody Tour tourRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Tour with id {}", id);
        tourService.update(id, tourRequest);
        logger.info("Updated Tour with id {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Tour with id {}", id);
        tourService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().build();
    }

}
