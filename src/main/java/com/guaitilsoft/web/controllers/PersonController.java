package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.services.PersonService;
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
@RequestMapping(path = "/api/person")
public class PersonController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){this.personService =personService;}

    @GetMapping
    public ResponseEntity<List<Person>> get(){
        return  ResponseEntity.ok().body(personService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getById(@PathVariable String id) throws Exception, EntityNotFoundException{
      logger.info("Fetching Person with id {}", id);
      return ResponseEntity.ok().body(personService.get(id));
    }

    @PostMapping
    public ResponseEntity<Person> post(@RequestBody Person person) throws  Exception{
       logger.info("Creating person: {}", person);
       personService.save(person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        logger.info("Created person: {}", person);

        return ResponseEntity.created(location).body(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable String id, @RequestBody Person personRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Person with id: {}", id);
        personService.update(id, personRequest);
        logger.info("Updated Person with id: {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Person with id: {}", id);
        personService.delete(id);
        logger.info("Deleted Person with id: {}", id);
        return ResponseEntity.ok().build();
    }
}
