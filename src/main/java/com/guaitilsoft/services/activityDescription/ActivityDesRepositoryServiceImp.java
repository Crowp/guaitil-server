package com.guaitilsoft.services.activityDescription;

import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.repositories.ActivityDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ActivityDesRepositoryServiceImp implements ActivityDesRepositoryService {

    private final ActivityDescriptionRepository activityDescriptionRepository;

    @Autowired
    public ActivityDesRepositoryServiceImp(ActivityDescriptionRepository activityDescriptionRepository) {
        this.activityDescriptionRepository = activityDescriptionRepository;
    }

    @Override
    public ActivityDescription get(Long id) {
        assert id != null;
        ActivityDescription activityDescription = activityDescriptionRepository.findById(id).orElse(null);
        if (activityDescription != null){
            return activityDescription;
        }
        throw new EntityNotFoundException("No se encontró el activity description con el id: " + id);
    }

    @Override
    public List<ActivityDescription> getActivityDescriptionNoRelationships() {
        return activityDescriptionRepository.getActivityDescriptionNoRelationships();
    }

    @Override
    public void delete(Long id) {
        assert id != null;
        ActivityDescription activityDescription = this.get(id);
        activityDescriptionRepository.delete(activityDescription);
    }
}
