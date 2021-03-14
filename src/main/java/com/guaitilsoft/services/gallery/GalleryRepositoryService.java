package com.guaitilsoft.services.gallery;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;

import java.util.List;
import java.util.Optional;

public interface GalleryRepositoryService {

    Optional<Gallery> get();

    Gallery getById(Long id);

    Gallery addMultimedia(List<Multimedia> multimediaList);

    Gallery deleteGalleryMultimedia(Long id);
}
