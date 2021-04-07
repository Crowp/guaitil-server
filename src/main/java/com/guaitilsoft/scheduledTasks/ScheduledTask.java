package com.guaitilsoft.scheduledTasks;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.ProductDescription;
import com.guaitilsoft.repositories.ActivityRepository;
import com.guaitilsoft.services.activityDescription.ActivityDesRepositoryService;
import com.guaitilsoft.services.localDescription.LocalDesRepositoryService;
import com.guaitilsoft.services.productDescription.ProductDesRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final LocalDesRepositoryService localDescriptionRepository;
    private final ProductDesRepositoryService productDescriptionRepository;
    private final ActivityDesRepositoryService activityDescriptionRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public ScheduledTask(LocalDesRepositoryService localDescriptionRepository,
                         ProductDesRepositoryService productDescriptionRepository,
                         ActivityDesRepositoryService activityDescriptionRepository,
                         ActivityRepository activityRepository) {
        this.localDescriptionRepository = localDescriptionRepository;
        this.productDescriptionRepository = productDescriptionRepository;
        this.activityDescriptionRepository = activityDescriptionRepository;
        this.activityRepository = activityRepository;
    }

    @Scheduled(cron = "30 1 31 * *")
    public void deleteLocalDescriptionWithoutRelationship(){
        List<LocalDescription> localDescriptions = localDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (localDescriptions.size() != 0) {
            localDescriptions.forEach(localDescription -> localDescriptionRepository.delete(localDescription.getId()));
            logger.info("Locals descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    @Scheduled(cron = "0 0 31 * *")
    public void deleteProductDescriptionWithoutRelationship(){
        List<ProductDescription> productDescriptions = productDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (productDescriptions.size() != 0) {
            productDescriptions.forEach(productDescription -> productDescriptionRepository.delete(productDescription.getId()));
            logger.info("Products descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    @Scheduled(cron = "0 1 31 * *")
    public void deleteActivityDescriptionWithoutRelationship(){
        List<ActivityDescription> activityDescriptions = activityDescriptionRepository.getActivityDescriptionNoRelationships();
        if (activityDescriptions.size() != 0) {
            activityDescriptions.forEach(activityDescription -> activityDescriptionRepository.delete(activityDescription.getId()));
            logger.info("Activities descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    //@Scheduled(cron = "1 * * * *")
    @Scheduled(cron = "30 23 * * 0")
    public void updateActivityIsActive(){
        List<Activity> activities = activityRepository.getActivitiesDone(LocalDateTime.now());

        if (activities.size() != 0){
            activities.forEach(activity -> {
                    activity.setIsActive(!activity.getIsActive());
                    activityRepository.save(activity);
                    logger.info("Activity update isActive {}", dateTimeFormatter.format(LocalDateTime.now()) );
            });
        }
    }
}
