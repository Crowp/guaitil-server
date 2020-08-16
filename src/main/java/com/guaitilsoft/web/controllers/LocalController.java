package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.PersonService;
import com.guaitilsoft.web.models.Local.LocalView;
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
@RequestMapping(path = "/api/local")
public class LocalController {

    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private LocalService localService;
    private PersonService personService;
    private ModelMapper modelMapper;

    @Autowired
    public LocalController(LocalService localService, PersonService personService, ModelMapper modelMapper) {
        this.localService = localService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Local>> get(){
        return  ResponseEntity.ok().body(localService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Local> getById(@PathVariable Long id) {
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(localService.get(id));
    }

    @PostMapping
    public ResponseEntity<LocalView> post(@RequestBody LocalView localRequest) {
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Creating local: {}", local);

        String personId = localRequest.getPersonId();
        local.setPerson(personService.get(personId));
        localService.save(local);

        LocalView localResponse = modelMapper.map(local, LocalView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(local.getId())
                .toUri();
        logger.info("Created local: {}", localResponse.getId());

        return ResponseEntity.created(location).body(localResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocalView> put(@PathVariable Long id, @RequestBody LocalView localRequest) {
        if(!id.equals(localRequest.getId())){
            throw new ApiRequestException("El id del asociado: " + localRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Updating Local with id: {}", id);

        String personId = localRequest.getPersonId();
        local.setPerson(personService.get(personId));
        localService.update(id, local);

        LocalView localResponse = modelMapper.map(local, LocalView.class);
        logger.info("Updated Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<LocalView> delete(@PathVariable Long id) {
        LocalView localResponse = modelMapper.map(id, LocalView.class);
        logger.info("Deleting Local with id: {}", id);
        localService.delete(id);
        logger.info("Deleted Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }
}
