package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ActivityHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityHistoryRepository extends CrudRepository<ActivityHistory, Long> {
}
