package com.guaitilsoft.services.activity;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.constant.TypeEmail;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.local.LocalRepositoryService;
import com.guaitilsoft.services.localDescription.LocalDesRepositoryService;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.utils.EmailActivityTemplate;
import com.guaitilsoft.utils.GuaitilEmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Service("ActivityRepositoryServiceValidation")
public class ActivityValidationRepositoryServiceImp implements ActivityRepositoryService {

    private final ActivityRepositoryService activityRepositoryService;
    private final LocalDesRepositoryService localDesRepositoryService;
    private final LocalRepositoryService localRepositoryService;
    private final UserRepositoryService userRepositoryService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ActivityValidationRepositoryServiceImp(ActivityRepositoryService activityRepositoryService,
                                                  LocalDesRepositoryService localDesRepositoryService,
                                                  LocalRepositoryService localRepositoryService,
                                                  UserRepositoryService userRepositoryService,
                                                  EmailSenderService emailSenderService) {
        this.activityRepositoryService = activityRepositoryService;
        this.localDesRepositoryService = localDesRepositoryService;
        this.localRepositoryService = localRepositoryService;
        this.userRepositoryService = userRepositoryService;
        this.emailSenderService = emailSenderService;
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
        this.sendEmailMembersWithLocals(activity);
        this.sendEmailToUsersAdmin(activity);
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

    private Set<LocalDescription> loadLocalDescription(Set<LocalDescription> localDescriptions){
        return localDescriptions
                .stream()
                .map(localDes -> localDesRepositoryService.get(localDes.getId()))
                .collect(Collectors.toSet());
    }

    private void sendEmailMembersWithLocals(Activity activity){
        activity.getLocalsDescriptions().forEach(l -> {
            Local local = this.localRepositoryService.getByLocalDescriptionId(l.getId());
            String personName = local.getFullMemberName();
            String localName = l.getLocalName();
            String activityName = activity.getActivityDescription().getName();
            LocalDateTime activityDate = activity.getActivityDescription().getActivityDate();
            String activityAddress = activity.getActivityDescription().getAddress().getPhysicalAddress();
            String template = new EmailActivityTemplate()
                    .addPersonName(personName)
                    .addLocalName(localName)
                    .addActivityName(activityName)
                    .addActivityDate(activityDate)
                    .addActivityAddress(activityAddress)
                    .addTypeInformation(TypeEmail.ACTIVITYMEMBER)
                    .getTemplate();

            emailSenderService.sendEmail("Has sido invitado a una nueva actividad", GuaitilEmailInfo.getEmailFrom(), local.getMember().getPerson().getEmail(), template);
        });
    }

    private void sendEmailToUsersAdmin(Activity activity){
        this.userRepositoryService.getUsersAdmin().forEach(user -> {
            String personName = user.getMember().getPerson().getName()+" "+user.getMember().getPerson().getFirstLastName()+" "+user.getMember().getPerson().getSecondLastName();
            String activityName = activity.getActivityDescription().getName();
            LocalDateTime activityDate = activity.getActivityDescription().getActivityDate();
            String activityType = activity.getActivityDescription().getActivityType().getMessage();
            String template = new EmailActivityTemplate()
                    .addPersonName(personName)
                    .addActivityName(activityName)
                    .addActivityDate(activityDate)
                    .addActivityType(activityType)
                    .addTypeInformation(TypeEmail.ACTIVITYADMIN)
                    .getTemplate();

            emailSenderService.sendEmail("Aviso de nueva actividad en GuaitilTour", GuaitilEmailInfo.getEmailFrom(), user.getMember().getPerson().getEmail(), template);
        });
    }
}
