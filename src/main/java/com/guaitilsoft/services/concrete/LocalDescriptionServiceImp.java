package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.repositories.LocalDescriptionRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalDescriptionService;
import com.guaitilsoft.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalDescriptionServiceImp implements LocalDescriptionService {

    private final LocalDescriptionRepository localDescriptionRepository;
    private final ActivityService activityService;
    private final LocalService localService;

    @Autowired
    public LocalDescriptionServiceImp(LocalDescriptionRepository localDescriptionRepository, ActivityService activityService, LocalService localService) {
        this.localDescriptionRepository = localDescriptionRepository;
        this.activityService = activityService;
        this.localService = localService;
    }

    @Override
    public List<LocalDescription> list() {
        Iterable<LocalDescription> iterable = localDescriptionRepository.findAll();
        List<LocalDescription> localDescriptions = new ArrayList<>();
        iterable.forEach(localDescriptions::add);
        return localDescriptions;
    }

    @Override
    public void deleteLocalsDescriptionsNotRelationships() {
        List<Local> locals = localService.list();

        locals.forEach(local -> this.list().forEach(localDescription -> ));
    }
}
