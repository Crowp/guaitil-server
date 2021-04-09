package com.guaitilsoft.services.activity;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.services.local.LocalRepositoryService;
import com.guaitilsoft.services.localDescription.LocalDesRepositoryService;
import com.guaitilsoft.services.notification.NotificationRepServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guaitilsoft.models.constant.NotificationMessage.ACTIVITY_NOTIFICATION;

@Primary
@Service("ActivityRepositoryServiceValidation")
public class ActivityValidationRepositoryServiceImp implements ActivityRepositoryService {

    private final ActivityRepositoryService activityRepositoryService;
    private final LocalDesRepositoryService localDesRepositoryService;
    private final LocalRepositoryService localRepositoryService;
    private final NotificationRepServ notificationRepServ;

    @Autowired
    public ActivityValidationRepositoryServiceImp(ActivityRepositoryService activityRepositoryService,
                                                  LocalDesRepositoryService localDesRepositoryService,
                                                  LocalRepositoryService localRepositoryService,
                                                  NotificationRepServ notificationRepServ) {
        this.activityRepositoryService = activityRepositoryService;
        this.localDesRepositoryService = localDesRepositoryService;
        this.localRepositoryService = localRepositoryService;
        this.notificationRepServ = notificationRepServ;
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
    public Activity save(Activity entity) {
        entity.setLocalsDescriptions(this.loadLocalDescription(entity.getLocalsDescriptions()));
        Activity activity = this.activityRepositoryService.save(entity);
        this.createNotification(activity);
        return activity;
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

    private void createNotification(Activity activity){
        List<Member> members = new ArrayList<>();
        String notification = ACTIVITY_NOTIFICATION.getMessage() + activity.getActivityDescription().getName();

        activity.getLocalsDescriptions().forEach(localDescription -> this.localRepositoryService.list().forEach(local -> {
            if (localDescription.getId().equals(local.getLocalDescription().getId())){
                members.add(local.getMember());
            }
        }));

        this.notificationRepServ.save(notification, members);
    }

    private Set<LocalDescription> loadLocalDescription(Set<LocalDescription> localDescriptions){
        return localDescriptions
                .stream()
                .map(localDes -> localDesRepositoryService.get(localDes.getId()))
                .collect(Collectors.toSet());
    }
}
