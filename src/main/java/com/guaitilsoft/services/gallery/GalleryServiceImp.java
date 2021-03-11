package com.guaitilsoft.services.gallery;

import com.guaitilsoft.models.Gallery;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.gallery.GalleryRequest;
import com.guaitilsoft.web.models.gallery.GalleryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImp implements GalleryService {

    private final GalleryRepositoryService galleryRepositoryService;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;

    public GalleryServiceImp(GalleryRepositoryService galleryRepositoryService,
                             MultimediaService multimediaService,
                             ModelMapper modelMapper) {
        this.galleryRepositoryService = galleryRepositoryService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
    }

    @Override
    public GalleryResponse get() {
        Optional<Gallery> optionalGallery = galleryRepositoryService.get();
        return optionalGallery.map(this::getGallery)
                .orElseGet(() -> this.parseToGalleryResponse(galleryRepositoryService.addMultimedia(new ArrayList<>())));
    }
    private GalleryResponse getGallery(Gallery gallery){
        GalleryResponse galleryResponse = this.parseToGalleryResponse(gallery);
        Utils.addUrlToMultimedia(galleryResponse.getMultimedia());
        return galleryResponse;
    }

    @Override
    public GalleryResponse addMultimedia(GalleryRequest galleryRequest) {
        List<Multimedia> multimedia = galleryRequest.getMultimedia();
        loadMultimedia(multimedia);
        GalleryResponse galleryResponse = this.parseToGalleryResponse(galleryRepositoryService.addMultimedia(multimedia));
        Utils.addUrlToMultimedia(galleryResponse.getMultimedia());
        return galleryResponse;
    }

    @Override
    public GalleryResponse deleteGalleryMultimedia(Long id) {
        return this.parseToGalleryResponse(galleryRepositoryService.deleteGalleryMultimedia(id));
    }

    @Override
    public GalleryResponse getById(Long id) {
        return null;
    }

    private GalleryResponse parseToGalleryResponse(Gallery gallery){
        return modelMapper.map(gallery, GalleryResponse.class);
    }

    private void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }
}
