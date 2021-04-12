package com.guaitilsoft.services.gallery;

import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;

public interface GalleryService {

    GalleryResponse get();

    GalleryResponse addMultimedia(GalleryRequest galleryRequest);

    GalleryResponse deleteGalleryMultimedia(Long id);

    GalleryResponse getById(Long id);
}
