package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {

}
