package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Tour;
import com.guaitilsoft.services.TourService;
import com.guaitilsoft.web.models.activity.ActivityView;
import com.guaitilsoft.web.models.tour.TourView;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    public TourController(TourService tourService, ModelMapper modelMapper){this.tourService =tourService;
        this.modelMapper = modelMapper;
    }

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
    public ResponseEntity<TourView> post(@RequestBody TourView tourRequest) throws Exception, EntityNotFoundException{
        Tour tour = modelMapper.map(tourRequest, Tour.class);
        logger.info("Creating tour: {}", tour);
        tourService.save(tour);
        TourView tourResponse = modelMapper.map(tour, TourView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tour.getId())
                .toUri();
        logger.info("Created activity : {}", tourResponse.getId());

        return ResponseEntity.created(location).body(tourResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<TourView> put(@PathVariable Long id, @RequestBody TourView tourRequest) throws Exception, EntityNotFoundException {
        if(!id.equals(tourRequest.getId())){
            throw new ApiRequestException("El id del tour: " + tourRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Tour tour = modelMapper.map(tourRequest, Tour.class);
        logger.info("Updating Tour with id: {}", id);
        tourService.update(id, tour);
        TourView tourResponse = modelMapper.map(tour, TourView.class);
        logger.info("Updated Tour with id: {}", id);
        return ResponseEntity.ok().body(tourResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TourView> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        TourView tourResponse = modelMapper.map(tourService.get(id), TourView.class);
        logger.info("Deleting Tour with id {}", id);
        tourService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().body(tourResponse);
    }
}
