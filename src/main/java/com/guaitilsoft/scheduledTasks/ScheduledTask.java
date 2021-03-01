package com.guaitilsoft.scheduledTasks;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.repositories.LocalDescriptionRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final LocalDescriptionRepository localDescriptionRepository;

    @Autowired
    public ScheduledTask(LocalDescriptionRepository localDescriptionRepository) {
        this.localDescriptionRepository = localDescriptionRepository;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void deleteLocalDescriptionWithoutRelationship(){
        List<LocalDescription> localDescriptions = localDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (localDescriptions.size() != 0) {
            localDescriptions.forEach(localDescriptionRepository::delete);
            logger.info("Locals descriptions deleted", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }
}
