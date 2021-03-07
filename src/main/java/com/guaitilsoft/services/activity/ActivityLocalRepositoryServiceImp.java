package com.guaitilsoft.services.activity;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.services.LocalDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("ActivityRepositoryServiceLocal")
public class ActivityLocalRepositoryServiceImp implements ActivityRepositoryService {

    private final ActivityRepositoryService activityRepositoryService;
    private final LocalDescriptionService localDescriptionService;

    @Autowired
    public ActivityLocalRepositoryServiceImp(@Qualifier("ActivityRepositoryServiceBasic") ActivityRepositoryService activityRepositoryService, LocalDescriptionService localDescriptionService) {
        this.activityRepositoryService = activityRepositoryService;
        this.localDescriptionService = localDescriptionService;
    }

    @Override
    public List<Activity> list() {
        return this.activityRepositoryService.list();
    }

    @Override
    public Activity get(Long id) {
        return this.activityRepositoryService.get(id);
    }

    @Override
    public Activity save(Activity activity) {
        activity.setLocalsDescriptions(this.loadLocalDescription(activity.getLocalsDescriptions()));
        return this.activityRepositoryService.save(activity);
    }

    @Override
    public Activity update(Long id, Activity entity) {
        entity.setLocalsDescriptions(this.loadLocalDescription(entity.getLocalsDescriptions()));
        return this.activityRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        this.activityRepositoryService.delete(id);
    }

    private Set<LocalDescription> loadLocalDescription(Set<LocalDescription> localDescriptions){
        return localDescriptions
                .stream()
                .map(localDes -> localDescriptionService.get(localDes.getId()))
                .collect(Collectors.toSet());
    }
}
