package com.guaitilsoft.repositories;

import com.guaitilsoft.models.LocalDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalDescriptionRepository extends CrudRepository<LocalDescription, Long> {
    @Query("SELECT locald FROM LocalDescription locald LEFT JOIN Local l ON locald.id=l. WHERE l.id IS NULL")
    List<LocalDescription> getLocalsDescriptionNoRelationships();
}
