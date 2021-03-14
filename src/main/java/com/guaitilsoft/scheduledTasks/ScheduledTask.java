package com.guaitilsoft.scheduledTasks;

import com.guaitilsoft.models.ActivityDescription;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.ProductDescription;
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

    @Autowired
    public ScheduledTask(LocalDesRepositoryService localDescriptionRepository,
                         ProductDesRepositoryService productDescriptionRepository,
                         ActivityDesRepositoryService activityDescriptionRepository) {
        this.localDescriptionRepository = localDescriptionRepository;
        this.productDescriptionRepository = productDescriptionRepository;
        this.activityDescriptionRepository = activityDescriptionRepository;
    }

    @Scheduled(cron = "0 0 23 31 * ?")
    public void deleteLocalDescriptionWithoutRelationship(){
        List<LocalDescription> localDescriptions = localDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (localDescriptions.size() != 0) {
            localDescriptions.forEach(localDescription -> localDescriptionRepository.delete(localDescription.getId()));
            logger.info("Locals descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    @Scheduled(cron = "0 0 23 30 * ?")
    public void deleteProductDescriptionWithoutRelationship(){
        List<ProductDescription> productDescriptions = productDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (productDescriptions.size() != 0) {
            productDescriptions.forEach(productDescription -> productDescriptionRepository.delete(productDescription.getId()));
            logger.info("Products descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    @Scheduled(cron = "0 0 23 29 * ?")
    public void deleteActivityDescriptionWithoutRelationship(){
        List<ActivityDescription> activityDescriptions = activityDescriptionRepository.getActivityDescriptionNoRelationships();
        if (activityDescriptions.size() != 0) {
            activityDescriptions.forEach(activityDescription -> activityDescriptionRepository.delete(activityDescription.getId()));
            logger.info("Activities descriptions deleted {}", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }
}
