package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.activityDescription.id IN " +
            "(SELECT ad FROM ActivityDescription ad WHERE  ad.activityDate < :activityDate) AND a.isActive=true")
    List<Activity> getActivitiesDone(@Param("activityDate") LocalDateTime activityDate);
}
