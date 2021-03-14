package com.guaitilsoft.utils;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.multimedia.MultimediaService;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    private final MultimediaService multimediaService;

    @Autowired
    public Utils(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    public void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }

    public static void addUrlToMultimedia(List<MultimediaResponse> multimedia){
        multimedia.forEach(m -> m.setUrl(getUrlHost(m)));
    }

    public static String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }

    public static String getDateReport(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return now.format(formatter);
    }
}
