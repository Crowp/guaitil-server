package com.guaitilsoft.services.gallery;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;

import java.util.List;

public interface GalleryService {

    GalleryResponse get();

    GalleryResponse addMultimedia(GalleryRequest galleryRequest);

    GalleryResponse deleteGalleryMultimedia(Long id);

    GalleryResponse getById(Long id);
}
