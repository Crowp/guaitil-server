package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Tour;
import com.guaitilsoft.services.TourService;
import com.guaitilsoft.web.models.tour.LoadTour;
import com.guaitilsoft.web.models.tour.TourView;
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
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/tour")
public class TourController {

    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private TourService tourService;
    private ModelMapper modelMapper;

    @Autowired
    public TourController(TourService tourService, ModelMapper modelMapper){
        this.tourService = tourService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TourView>> get(){
        Type listType  = new TypeToken<List<TourView>>(){}.getType();
        List<TourView> tours = modelMapper.map(tourService.list(),listType);
        return  ResponseEntity.ok().body(tours);
    }

    @GetMapping("{id}")
    public ResponseEntity<TourView> getById(@PathVariable Long id) throws Exception {
        TourView tour = modelMapper.map(tourService.get(id), TourView.class);
        logger.info("Fetching Tour with id {}", id);
        return ResponseEntity.ok().body(tour);
    }

    @PostMapping
    public ResponseEntity<TourView> post(@RequestBody TourView tourRequest) throws Exception {
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
    public ResponseEntity<LoadTour> put(@PathVariable Long id, @RequestBody LoadTour tourRequest) throws Exception {
        if(!id.equals(tourRequest.getId())){
            throw new ApiRequestException("El id del tour: " + tourRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Tour tour = modelMapper.map(tourRequest, Tour.class);
        logger.info("Updating Tour with id: {}", id);
        tourService.update(id, tour);
        LoadTour tourResponse = modelMapper.map(tour, LoadTour.class);
        logger.info("Updated Tour with id: {}", id);
        return ResponseEntity.ok().body(tourResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<LoadTour> delete(@PathVariable Long id) throws Exception {
        LoadTour tourResponse = modelMapper.map(tourService.get(id), LoadTour.class);
        logger.info("Deleting Tour with id {}", id);
        tourService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().body(tourResponse);
    }
}
