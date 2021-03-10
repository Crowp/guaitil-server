package com.guaitilsoft.services.activity;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.services.localDescription.LocalDesRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Service("ActivityRepositoryServiceValidation")
public class ActivityValidationRepositoryServiceImp implements ActivityRepositoryService {

    private final ActivityRepositoryService activityRepositoryService;
    private final LocalDesRepositoryService localDesRepositoryService;

    @Autowired
    public ActivityValidationRepositoryServiceImp(@Qualifier("ActivityRepositoryServiceBasic") ActivityRepositoryService activityRepositoryService, LocalDesRepositoryService localDesRepositoryService) {
        this.activityRepositoryService = activityRepositoryService;
        this.localDesRepositoryService = localDesRepositoryService;
    }

    @Override
    public List<Activity> list() {
        return this.activityRepositoryService.list();
    }

    @Override
    public Activity get(Long id) {
        Activity activity = activityRepositoryService.get(id);
        if(activity != null){
            return activity;
        }
        throw new EntityNotFoundException("No se encontró una actividad con el id: " + id);
    }

    @Override
    public Activity save(Activity activity) {
        activity.setLocalsDescriptions(this.loadLocalDescription(activity.getLocalsDescriptions()));
        return this.activityRepositoryService.save(activity);
    }

    @Override
    public Activity update(Long id, Activity entity) {
        if (!id.equals(entity.getId())) {
            throw new ApiRequestException("El id de la actividad: " + entity.getId() + " es diferente al id del parametro: " + id);
        }

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
                .map(localDes -> localDesRepositoryService.get(localDes.getId()))
                .collect(Collectors.toSet());
    }
}
