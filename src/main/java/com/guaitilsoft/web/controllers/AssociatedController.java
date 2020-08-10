package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Associated;
import com.guaitilsoft.services.concrete.AssociatedService;
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
@RequestMapping(path = "/api/associated")
public class AssociatedController {
    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private AssociatedService associatedService;

    @Autowired
    public AssociatedController(AssociatedService associatedService){this.associatedService =associatedService;}


    @GetMapping
    public ResponseEntity<List<Associated>> get(){
        return  ResponseEntity.ok().body(associatedService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Associated> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Associated with id {}", id);
        return ResponseEntity.ok().body(associatedService.get(id));
    }
    @PostMapping
    public ResponseEntity<Associated> post(@RequestBody Associated associated) throws  Exception{
        logger.info("Creating Associated", associated);
        associatedService.save(associated);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(associated.getId())
                .toUri();
        logger.info("Created Associated : {}", associated);

        return ResponseEntity.created(location).body(associated);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestBody Associated associatedRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Associated with id {}", id);
        associatedService.update(id, associatedRequest);
        logger.info("Updated Associated with id {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Associated with id {}", id);
        associatedService.delete(id);
        logger.info("Deleted Associated with id {}", id);
        return ResponseEntity.ok().build();
    }
}
