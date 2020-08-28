package com.guaitilsoft.services;

import com.guaitilsoft.models.Gallery;

import java.util.List;

public interface GalleryService {
    List<Gallery> list();

    Gallery get(Long id);

    void save(Gallery entity);

    void update(Long id, Gallery entity);

    void delete(Long id);

}
