package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.GalleryService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/gallery")
public class GalleryController {

    public static final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    private final GalleryService galleryService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public GalleryController(
            GalleryService galleryService,
            ModelMapper modelMapper,
            Utils utils){
        this.galleryService  = galleryService;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }

    @GetMapping
    public ResponseEntity<GalleryResponse> get(){
        Optional<Gallery> optionalGallery = galleryService.get();
        if(optionalGallery.isPresent()){
            GalleryResponse gallery = this.modelMapper.map(optionalGallery.get(), GalleryResponse.class);
            this.utils.addUrlToMultimedia(gallery.getMultimedia());
            return  ResponseEntity.ok().body(gallery);
        }
        GalleryResponse gallery = this.modelMapper.map(galleryService.addMultimedia(new ArrayList<>()), GalleryResponse.class);

        return  ResponseEntity.ok().body(gallery);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GalleryResponse> delete(@PathVariable Long id){
        GalleryResponse gallery = this.modelMapper.map(galleryService.deleteGalleryMultimedia(id), GalleryResponse.class);
        return  ResponseEntity.ok().body(gallery);
    }

    @PostMapping
    public ResponseEntity<GalleryResponse> post(@RequestBody GalleryRequest galleryRequest) {
        logger.info("Adding multimedia to gallery");
        List<Multimedia> multimediaList = galleryRequest.getMultimedia();
        utils.loadMultimedia(multimediaList);
        GalleryResponse gallery = this.modelMapper.map(galleryService.addMultimedia(multimediaList), GalleryResponse.class);
        this.utils.addUrlToMultimedia(gallery.getMultimedia());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gallery.getId())
                .toUri();
        logger.info("Add Multimedia to gallery : {}", gallery.getId());

        return ResponseEntity.created(location).body(gallery);
    }
}
