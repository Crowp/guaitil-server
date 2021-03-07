package com.guaitilsoft.utils;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    private final MultimediaService multimediaService;
    private final LocalService localService;
    private final MemberRepositoryService memberRepositoryService;


    @Autowired
    public Utils(MultimediaService multimediaService,
                 LocalService localService,
                 @Qualifier("MemberRepositoryServiceValidation") MemberRepositoryService memberRepositoryService) {
        this.multimediaService = multimediaService;
        this.localService = localService;
        this.memberRepositoryService = memberRepositoryService;
    }

    public Local loadFullLocal(Long id){
        return this.localService.get(id);
    }

    public LocalDescription loadFullLocalDescriptionByLocalId(Long id){
        return this.localService.get(id).getLocalDescription();
    }

    public Member loadFullMember(Long id){
        return this.memberRepositoryService.get(id);
    }

    public void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }

    public void addUrlToMultimedia(List<MultimediaResponse> multimedia){
        multimedia.forEach(m -> m.setUrl(getUrlHost(m)));
    }

    public static String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
