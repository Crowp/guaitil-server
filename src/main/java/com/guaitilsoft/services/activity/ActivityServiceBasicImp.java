package com.guaitilsoft.services.activity;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

@Service("ActivityServiceBasic")
public class ActivityServiceBasicImp implements ActivityServiceBasic {
    
    private final ActivityRepositoryService activityRepositoryService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public ActivityServiceBasicImp(ActivityRepositoryService activityRepositoryService,
                                   ModelMapper modelMapper,
                                   Utils utils) {
        this.activityRepositoryService = activityRepositoryService;
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
    public ActivityResponse get(Long id) {
        Activity activity = this.activityRepositoryService.get(id);
        if(activity != null){
            return getActivityResponse(activity);
        }
        throw new EntityNotFoundException("No se encontró una actividad con el id: " + id);
    }

    @Override
    public ActivityResponse save(ActivityRequest entity) {
        Activity activity = this.parseToActivity(entity);
        this.loadLocalDescriptions(entity, activity);
        return onSaveActivity(activity);
    }

    private ActivityResponse onSaveActivity(Activity activityToStore) {
        this.utils.loadMultimedia(activityToStore.getMultimedia());
        Activity activityStored =this.activityRepositoryService.save(activityToStore);
        return getActivityResponse(activityStored);
    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest entity) {

        if (!id.equals(entity.getId())) {
            throw new ApiRequestException("El id de la actividad: " + entity.getId() + " es diferente al id del parametro: " + id);
        }

        Activity activity =  activityRepositoryService.update(id, this.parseToActivity(entity));
        return getActivityResponse(activity);
    }

    @Override
    public void delete(Long id) {
        activityRepositoryService.delete(id);
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

    private void loadLocalDescriptions(ActivityRequest activityRequest, Activity activity) {
        activity.getLocalsDescriptions().clear();
        activityRequest.getLocals().forEach(local -> {
            LocalDescription localDescription = this.utils.loadFullLocalDescriptionByLocalId(local.getId());
            activity.getLocalsDescriptions().add(localDescription);
        });
    }
}
