package com.guaitilsoft.services.gallery;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Primary
@Service("GalleryRepositoryServiceValidation")
public class GalleryValidationRepositoryServiceImp implements GalleryRepositoryService {

    private final GalleryRepositoryService galleryRepositoryService;

    public GalleryValidationRepositoryServiceImp(GalleryRepositoryService galleryRepositoryService) {
        this.galleryRepositoryService = galleryRepositoryService;
    }

    @Override
    public Optional<Gallery> get() {
        return galleryRepositoryService.get();
    }

    @Override
    public Gallery getById(Long id) {
        Gallery gallery = galleryRepositoryService.getById(id);
        if (gallery != null){
            return gallery;
        }
        throw new EntityNotFoundException("No se encontró una galería con el id: " + id);
    }

    @Override
    public Gallery addMultimedia(List<Multimedia> multimediaList) {
        return galleryRepositoryService.addMultimedia(multimediaList);
    }

    @Override
    public Gallery deleteGalleryMultimedia(Long id) {
        return galleryRepositoryService.deleteGalleryMultimedia(id);
    }
}
