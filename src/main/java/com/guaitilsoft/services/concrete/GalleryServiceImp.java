package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.GalleryRepository;
import com.guaitilsoft.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImp implements GalleryService {

    private GalleryRepository galleryRepository;

    @Autowired
    public GalleryServiceImp(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }


    @Override
    public Optional<Gallery> get() {
        Iterable<Gallery> iterable = galleryRepository.findAll();
        List<Gallery> galleries = new ArrayList<>();
        iterable.forEach(galleries::add);
        return galleries.stream().findFirst();
    }

    @Override
    public Gallery addMultimedia(List<Multimedia> multimediaList) {
        Optional<Gallery> optionalGallery = this.get();
        if(optionalGallery.isPresent()){
            Gallery gallery = optionalGallery.get();
            multimediaList.addAll(gallery.getMultimedia());
            gallery.setMultimedia(multimediaList);
            galleryRepository.save(gallery);
            return gallery;
        } else {
            Gallery gallery = new Gallery();
            gallery.setMultimedia(multimediaList);
            gallery.setCreatedAt(new Date());
            gallery.setCreatedAt(new Date());
            galleryRepository.save(gallery);
            return gallery;
        }
    }

    @Override
    public Gallery deleteAllMultimedia() {
        Optional<Gallery> optionalGallery = this.get();
        if(optionalGallery.isPresent()){

        }
        return null;
    }
}
