package com.guaitilsoft.services.activity;

import com.guaitilsoft.services.MultimediaService;

import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImp implements ActivityService {

    private final ActivityServiceBasic activityServiceBasic;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActivityServiceImp(@Qualifier("ActivityServiceBasic") ActivityServiceBasic activityServiceBasic,
                              MultimediaService multimediaService,
                              ModelMapper modelMapper) {
        this.activityServiceBasic = activityServiceBasic;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ActivityResponse> getAllActivitiesActive() {
        return this.list().stream()
                .filter(ActivityResponse::getIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse deleteMultimediaById(Long id, Long idMedia) {
        ActivityResponse activityResponse = this.activityServiceBasic.get(id);
       activityResponse.removeMultimediaById(idMedia);

        ActivityRequest activityRequest = parseToActivityRequest(activityResponse);
        ActivityResponse activityStored = this.activityServiceBasic.update(id, activityRequest);

        this.multimediaService.delete(id);
        return activityStored;
    }

    private ActivityRequest parseToActivityRequest(ActivityResponse activity) {
        return this.modelMapper.map(activity, ActivityRequest.class);
    }

    @Override
    public List<ActivityResponse> list() {
        return this.activityServiceBasic.list();
    }

    @Override
    public ActivityResponse get(Long id) {
        return this.activityServiceBasic.get(id);
    }

    @Override
    public ActivityResponse save(ActivityRequest entity) {
        return this.activityServiceBasic.save(entity);
    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest entity) {
        return this.activityServiceBasic.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        this.activityServiceBasic.delete(id);
    }
}
