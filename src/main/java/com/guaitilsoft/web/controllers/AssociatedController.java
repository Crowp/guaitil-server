package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Associated;
import com.guaitilsoft.services.AssociatedService;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.web.models.associated.AssociatedRequest;
import com.guaitilsoft.web.models.associated.AssociatedResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/associated")
public class AssociatedController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private AssociatedService associatedService;
    private PersonService personService;
    private ModelMapper modelMapper;

    @Autowired
    public AssociatedController(AssociatedService associatedService, PersonService personService, ModelMapper modelMapper) {
        this.associatedService = associatedService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Associated>> get(){
        return  ResponseEntity.ok().body(associatedService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Associated> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Associated with id: {}", id);
        return ResponseEntity.ok().body(associatedService.get(id));
    }

    @PostMapping
    public ResponseEntity<AssociatedResponse> post(@RequestBody AssociatedRequest associatedRequest) {
        Associated associated = modelMapper.map(associatedRequest, Associated.class);
        logger.info("Creating Associated");

        if(associatedRequest.getPersonId() != null){
            String personId = associatedRequest.getPersonId();
            associated.setPerson(personService.get(personId));
        }
        associatedService.save(associated);

        AssociatedResponse associatedResponse = modelMapper.map(associated, AssociatedResponse.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(associated.getId())
                .toUri();
        logger.info("Created Associated: {}", associatedResponse.getId());

        return ResponseEntity.created(location).body(associatedResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<AssociatedResponse> put(@PathVariable Long id, @RequestBody @Valid AssociatedRequest associatedRequest) {
        if(!id.equals(associatedRequest.getId())){
            throw new ApiRequestException("El id del asociado: " + associatedRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Associated associated = modelMapper.map(associatedRequest, Associated.class);
        logger.info("Updating Associated with id: {}", id);
        associatedService.update(id, associated);
        AssociatedResponse associatedResponse = modelMapper.map(associated, AssociatedResponse.class);
        logger.info("Updated Associated with id: {}", id);
        return ResponseEntity.ok().body(associatedResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AssociatedResponse> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        AssociatedResponse associatedResponse = modelMapper.map(associatedService.get(id), AssociatedResponse.class);
        logger.info("Deleting Associated with id: {}", id);
        associatedService.delete(id);
        logger.info("Deleted Associated with id: {}", id);
        return ResponseEntity.ok().body(associatedResponse);
    }
}
