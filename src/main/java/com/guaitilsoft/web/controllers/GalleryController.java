package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.GalleryService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.gallery.GalleryView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/gallery")
public class GalleryController {


    public static final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    private GalleryService galleryService;
    private MultimediaService multimediaService;
    private ModelMapper modelMapper;

    @Autowired
    public GalleryController(GalleryService galleryService,MultimediaService multimediaService, ModelMapper modelMapper){
        this.galleryService  = galleryService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Gallery>> get(){
        return  ResponseEntity.ok().body(galleryService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Gallery> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Gallery with id {}", id);
        return ResponseEntity.ok().body(galleryService.get(id));
    }

    @PostMapping
    public ResponseEntity<GalleryView> post(@RequestBody GalleryView galleryRequest) throws  Exception{
        Gallery gallery = modelMapper.map(galleryRequest, Gallery.class);
        logger.info("Creating gallery");
        if(gallery.getMultimedia().size() > 0){
            List<Multimedia> multimediaList = new ArrayList<>();
            gallery.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            gallery.setMultimedia(multimediaList);
        }
        galleryService.save(gallery);
        GalleryView galleryResponse = modelMapper.map(gallery, GalleryView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gallery.getId())
                .toUri();
        logger.info("Created gallery : {}", galleryRequest.getId());

        return ResponseEntity.created(location).body(galleryResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<GalleryView> put(@PathVariable Long id, @RequestBody GalleryView galleryRequest) throws Exception, EntityNotFoundException {
        if(!id.equals(galleryRequest.getId())){
            throw new ApiRequestException("El id de la gallery: " + galleryRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Gallery gallery = modelMapper.map(galleryRequest, Gallery.class);
        logger.info("Updating Gallery with id {}", id);
        galleryService.update(id, gallery);
        GalleryView galleryResponse = modelMapper.map(gallery,GalleryView.class);
        logger.info("Updated Gallery with id {}", id);
        return ResponseEntity.ok().body(galleryResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GalleryView> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        GalleryView galleryResponse = modelMapper.map(galleryService.get(id), GalleryView.class);
        logger.info("Deleting Gallery with id {}", id);
        galleryService.delete(id);
        logger.info("Deleted Gallery with id {}", id);
        return ResponseEntity.ok().body(galleryResponse);
    }
}
