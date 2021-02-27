package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Person;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.web.models.person.PersonRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/people")
public class PersonController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper){
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Person>> get(){
        return  ResponseEntity.ok().body(personService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getById(@PathVariable String id) {
      logger.info("Fetching Person with id {}", id);
      return ResponseEntity.ok().body(personService.get(id));
    }

    @PostMapping
    public ResponseEntity<PersonRequest> post(@RequestBody PersonRequest personRequest) {
        Person person = modelMapper.map(personRequest, Person.class);
        logger.info("Creating person");
        personService.save(person);

        PersonRequest personResponse = modelMapper.map(person, PersonRequest.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        logger.info("Created person: {}", personResponse.getId());

        return ResponseEntity.created(location).body(personResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonRequest> put(@PathVariable String id, @RequestBody PersonRequest personRequest) {
        Person person = modelMapper.map(personRequest, Person.class);
        logger.info("Updating Person with id: {}", id);
        personService.update(id, person);
        PersonRequest personResponse = modelMapper.map(person, PersonRequest.class);
        logger.info("Updated Person with id: {}", id);
        return ResponseEntity.ok().body(personResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PersonRequest> delete(@PathVariable String id) {
        PersonRequest personResponse = modelMapper.map(personService.get(id), PersonRequest.class);
        logger.info("Deleting Person with id: {}", id);
        personService.delete(id);
        logger.info("Deleted Person with id: {}", id);
        return ResponseEntity.ok().body(personResponse);
    }
}
