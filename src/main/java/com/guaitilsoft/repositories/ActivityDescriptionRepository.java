package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ActivityDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityDescriptionRepository extends CrudRepository<ActivityDescription, Long> {
    @Query("SELECT actid FROM ActivityDescription actid LEFT JOIN Activity a ON actid.id=a.activityDescription.id " +
                                                "LEFT JOIN Reservation r ON actid.id=r.activityDescription.id " +
            "WHERE a.activityDescription.id IS NULL AND r.activityDescription.id IS NULL AND t.activityDescription.id IS NULL")
    List<ActivityDescription> getActivityDescriptionNoRelationships();
}

