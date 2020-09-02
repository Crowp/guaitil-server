package com.guaitilsoft.services;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;

import java.util.List;
import java.util.Optional;

public interface GalleryService {

    Optional<Gallery> get();

    Gallery addMultimedia(List<Multimedia> multimediaList);

    Gallery deleteGalleryMultimedia(Long id);

    Gallery getById(Long id);

    Gallery deleteMultimediaById(Long id, Long idMultimedia);
}
