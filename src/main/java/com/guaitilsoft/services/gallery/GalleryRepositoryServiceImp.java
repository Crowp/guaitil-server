package com.guaitilsoft.services.gallery;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.GalleryRepository;
import com.guaitilsoft.services.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("GalleryRepositoryServiceBasic")
public class GalleryRepositoryServiceImp implements GalleryRepositoryService {

    private final GalleryRepository galleryRepository;
    private final MultimediaService multimediaService;

    @Autowired
    public GalleryRepositoryServiceImp(GalleryRepository galleryRepository, MultimediaService multimediaService) {
        this.galleryRepository = galleryRepository;
        this.multimediaService = multimediaService;
    }

    @Override
    public Gallery getById(Long id) {
        assert id != null;
        return galleryRepository.findById(id).orElse(null);
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
        Gallery gallery;
        if(optionalGallery.isPresent()){
            gallery = optionalGallery.get();
            multimediaList.addAll(gallery.getMultimedia());
        } else {
            gallery = new Gallery();
        }
        gallery.setMultimedia(multimediaList);
        galleryRepository.save(gallery);
        return gallery;
    }

    @Override
    public Gallery deleteGalleryMultimedia(Long id) {
        Optional<Gallery> optionalGallery = this.get();
        if(optionalGallery.isPresent()){
            Gallery gallery = optionalGallery.get();
            List<Multimedia> multimedia = gallery.getMultimedia()
                    .stream()
                    .filter(media -> !media.getId().equals(id))
                    .collect(Collectors.toList());
            gallery.setMultimedia(multimedia);
            galleryRepository.save(gallery);
            multimediaService.delete(id);
            return gallery;
        }
        throw new ApiRequestException("No hay imagenes para eliminar");
    }
}
