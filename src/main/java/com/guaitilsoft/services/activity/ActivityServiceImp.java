package com.guaitilsoft.services.activity;

import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImp extends ActivityServiceBasicImp implements ActivityService {


    private final MultimediaService multimediaService;

    @Autowired
    public ActivityServiceImp(ActivityRepositoryService activityRepositoryService,
                              ModelMapper modelMapper,
                              Utils utils,
                              MultimediaService multimediaService) {
        super(activityRepositoryService, modelMapper, utils);
        this.multimediaService = multimediaService;
    }

    @Override
    public List<ActivityResponse> getAllActivitiesActive() {
        return this.list().stream()
                .filter(ActivityResponse::getIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse deleteMultimediaById(Long id, Long idMedia) {
        ActivityResponse activityResponse = this.get(id);
       activityResponse.removeMultimediaById(idMedia);

        ActivityRequest activityRequest = this.parseToActivityRequest(activityResponse);
        ActivityResponse activityStored = this.update(id, activityRequest);

        this.multimediaService.delete(id);
        return activityStored;
    }

}
