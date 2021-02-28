package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ActivityHistory;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityHistoryRepository extends CrudRepository<ActivityHistory, Long> {
    @Query("SELECT ld FROM LocalDescription ld INNER JOIN Activity a ON )")
    List<LocalDescription> getLocalsDescripNoRelationships();
}
