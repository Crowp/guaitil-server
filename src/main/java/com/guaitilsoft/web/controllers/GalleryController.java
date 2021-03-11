package com.guaitilsoft.web.controllers;

import com.guaitilsoft.services.gallery.GalleryService;
import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/gallery")
public class GalleryController {

    public static final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    private final GalleryService galleryService;

    @Autowired
    public GalleryController(
            GalleryService galleryService){
        this.galleryService  = galleryService;
    }

    @GetMapping
    public ResponseEntity<GalleryResponse> get(){
        GalleryResponse gallery = galleryService.get();

        return  ResponseEntity.ok().body(gallery);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GalleryResponse> delete(@PathVariable Long id){
        GalleryResponse gallery = galleryService.deleteGalleryMultimedia(id);
        return  ResponseEntity.ok().body(gallery);
    }

    @PostMapping
    public ResponseEntity<GalleryResponse> post(@RequestBody GalleryRequest galleryRequest) {
        logger.info("Adding multimedia to gallery");
        GalleryResponse gallery = galleryService.addMultimedia(galleryRequest);
        URI location = getUriResourceLocation(gallery.getId());
        logger.info("Add Multimedia to gallery : {}", gallery.getId());

        return ResponseEntity.created(location).body(gallery);
    }
    private URI getUriResourceLocation(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
