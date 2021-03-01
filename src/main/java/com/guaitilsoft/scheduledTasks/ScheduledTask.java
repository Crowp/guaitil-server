package com.guaitilsoft.scheduledTasks;

import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.ProductDescription;
import com.guaitilsoft.repositories.LocalDescriptionRepository;
import com.guaitilsoft.repositories.ProductDescriptionRepository;
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
    private final LocalDescriptionRepository localDescriptionRepository;
    private final ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    public ScheduledTask(LocalDescriptionRepository localDescriptionRepository, ProductDescriptionRepository productDescriptionRepository) {
        this.localDescriptionRepository = localDescriptionRepository;
        this.productDescriptionRepository = productDescriptionRepository;
    }

   // @Scheduled(cron = "0 * * * * ?")
    public void deleteLocalDescriptionWithoutRelationship(){
        List<LocalDescription> localDescriptions = localDescriptionRepository.getLocalsDescriptionNoRelationships();
        if (localDescriptions.size() != 0) {
            localDescriptions.forEach(localDescriptionRepository::delete);
            logger.info("Locals descriptions deleted", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void deleteProductDescriptionWithoutRelationship(){
        List<ProductDescription> productDescriptions = productDescriptionRepository.getProductsDescriptionNoRelationships();
        if (productDescriptions.size() != 0) {
            productDescriptions.forEach(productDescriptionRepository::delete);
            logger.info("Products descriptions deleted", dateTimeFormatter.format(LocalDateTime.now()) );
        }
    }
}
