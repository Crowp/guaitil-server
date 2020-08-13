package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Associated;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends CrudRepository<Associated, Long> {

}
