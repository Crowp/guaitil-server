package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.GalleryService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
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

    private GalleryService galleryService;
    private MultimediaService multimediaService;
    private ModelMapper mapper;

    @Autowired
    public GalleryController(
            GalleryService galleryService,
            MultimediaService multimediaService,
            ModelMapper modelMapper){
        this.galleryService  = galleryService;
        this.multimediaService = multimediaService;
        this.mapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<GalleryResponse> get(){
        Optional<Gallery> optionalGallery = galleryService.get();
        if(optionalGallery.isPresent()){
            GalleryResponse gallery = this.mapper.map(optionalGallery.get(), GalleryResponse.class);
            addUrlToMultimedia(gallery);
            return  ResponseEntity.ok().body(gallery);
        }
        GalleryResponse gallery = this.mapper.map(galleryService.addMultimedia(new ArrayList<>()), GalleryResponse.class);

        return  ResponseEntity.ok().body(gallery);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GalleryResponse> delete(@PathVariable Long id){
        GalleryResponse gallery = this.mapper.map(galleryService.deleteGalleryMultimedia(id), GalleryResponse.class);
        return  ResponseEntity.ok().body(gallery);
    }

    @PostMapping
    public ResponseEntity<GalleryResponse> post(@RequestBody GalleryRequest galleryRequest) throws  Exception{
        logger.info("Adding multimedia to gallery");
            List<Multimedia> multimediaList = new ArrayList<>();
            galleryRequest.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
        GalleryResponse gallery = this.mapper.map(galleryService.addMultimedia(multimediaList), GalleryResponse.class);
        addUrlToMultimedia(gallery);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gallery.getId())
                .toUri();
        logger.info("Add Multimedia to gallery : {}", gallery.getId());

        return ResponseEntity.created(location).body(gallery);
    }

    private void addUrlToMultimedia(GalleryResponse gallery) {
        gallery.getMultimedia().forEach(m -> {
            String url = getUrlHost(m);
            m.setUrl(url);
        });
    }

    private String getUrlHost(MultimediaResponse multimediaResponse) {
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
