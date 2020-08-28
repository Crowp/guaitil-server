package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.repositories.GalleryRepository;
import com.guaitilsoft.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryServiceImp implements GalleryService {

    private GalleryRepository galleryRepository;

    @Autowired
    public GalleryServiceImp(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Override
    public List<Gallery> list() {
        Iterable<Gallery> iterable = galleryRepository.findAll();
        List<Gallery> galleries = new ArrayList<>();
        iterable.forEach(galleries::add);
        return galleries;
    }

    @Override
    public Gallery get(Long id) {
        assert id != null;

        Gallery gallery = galleryRepository.findById(id).orElse(null);
        if(gallery != null){
            return gallery;
        }
        throw new EntityNotFoundException("No se encontró una galeria con el id: ");
    }

    @Override
    public void save(Gallery entity)  {
        assert entity != null;

        galleryRepository.save(entity);
    }

    @Override
    public void update(Long id, Gallery entity) {
        assert id != null;
        assert entity != null;

        Gallery gallery = this.get(id);
        gallery.setName(entity.getName());
        gallery.setDescription(entity.getDescription());
        gallery.setMultimedia(entity.getMultimedia());

        galleryRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Gallery gallery = this.get(id);
        galleryRepository.delete(gallery);
    }
}
