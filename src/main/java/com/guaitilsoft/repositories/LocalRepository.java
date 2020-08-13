package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends CrudRepository<Local, Long> {

}
