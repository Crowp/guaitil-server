package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.services.LocalService;
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
@RequestMapping(path = "/api/local")
public class LocalController {
    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private LocalService localService;


    @Autowired
    public LocalController(LocalService localService){this.localService =localService;}

    @GetMapping
    public ResponseEntity<List<Local>> get(){
        return  ResponseEntity.ok().body(localService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Local> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Local with id {}", id);
        return ResponseEntity.ok().body(localService.get(id));
    }

    @PostMapping
    public ResponseEntity<Local> post(@RequestBody Local local) throws  Exception{
        logger.info("Creating local", local);
        localService.save(local);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(local.getId())
                .toUri();
        logger.info("Created local : {}", local);

        return ResponseEntity.created(location).body(local);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestBody Local localRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Local with id {}", id);
        localService.update(id, localRequest);
        logger.info("Updated Local with id {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Local with id {}", id);
        localService.delete(id);
        logger.info("Deleted Local with id {}", id);
        return ResponseEntity.ok().build();
    }
}
