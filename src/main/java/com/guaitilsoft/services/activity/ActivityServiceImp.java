package com.guaitilsoft.services.activity;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service("ActivityServiceBasic")
public class ActivityServiceImp implements ActivityService {
    
    private final ActivityRepositoryService activityRepositoryService;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    public ActivityServiceImp(@Qualifier("ActivityRepositoryServiceValidation") ActivityRepositoryService activityRepositoryService,
                              MultimediaService multimediaService, ModelMapper modelMapper,
                              Utils utils) {
        this.activityRepositoryService = activityRepositoryService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }

    @Override
    public List<ActivityResponse> list() {
        List<ActivityResponse> activitiesResponses = this.parseToActivityResponseList(activityRepositoryService.list());
        activitiesResponses.forEach(a -> this.utils.addUrlToMultimedia(a.getMultimedia()));
        return activitiesResponses;
    }

    @Override
    public List<ActivityResponse> getAllActivitiesActive() {
        return this.list().stream()
                .filter(ActivityResponse::getIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse get(Long id) {
        return getActivityResponse(activityRepositoryService.get(id));
    }

    @Override
    public ActivityResponse save(ActivityRequest entity) {
        Activity activity = this.parseToActivity(entity);
        return onSaveActivity(activity);
    }

    private ActivityResponse onSaveActivity(Activity activityToStore) {
        this.utils.loadMultimedia(activityToStore.getMultimedia());
        Activity activityStored =this.activityRepositoryService.save(activityToStore);
        return getActivityResponse(activityStored);
    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest entity) {
        return getActivityResponse(activityRepositoryService.update(id, this.parseToActivity(entity)));
    }

    @Override
    public void delete(Long id) {
        activityRepositoryService.delete(id);
    }

    @Override
    public ActivityResponse deleteMultimediaById(Long id, Long idMedia) {
        ActivityResponse activityResponse = this.get(id);
        activityResponse.removeMultimediaById(idMedia);

        ActivityRequest activityRequest = parseToActivityRequest(activityResponse);
        ActivityResponse activityStored = this.update(id, activityRequest);

        this.multimediaService.delete(id);
        return activityStored;
    }

    private ActivityRequest parseToActivityRequest(ActivityResponse activity) {
        return this.modelMapper.map(activity, ActivityRequest.class);
    }


    private ActivityResponse getActivityResponse(Activity activityStored) {
        ActivityResponse activityResponse = this.parseToActivityResponse(activityStored);
        this.utils.addUrlToMultimedia(activityResponse.getMultimedia());
        return activityResponse;
    }

    private List<ActivityResponse> parseToActivityResponseList(List<Activity> activities){
        Type listType = new TypeToken<List<ActivityResponse>>() {}.getType();
        return this.modelMapper.map(activities, listType);
    }

    private ActivityResponse parseToActivityResponse(Activity activity){
        return this.modelMapper.map(activity, ActivityResponse.class);
    }

    public Activity parseToActivity(ActivityRequest activityRequest){
        return this.modelMapper.map(activityRequest, Activity.class);
    }
}
