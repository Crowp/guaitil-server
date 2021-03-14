package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Multimedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends CrudRepository<Multimedia, Long> {
}
