package com.guaitilsoft.repositories;

import com.guaitilsoft.models.LocalDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalDescriptionRepository extends CrudRepository<LocalDescription, Long> {
    @Query("SELECT locald FROM LocalDescription locald, Activity a LEFT JOIN Local l ON locald.id=l.localDescription.id " +
            " LEFT JOIN a.localsDescriptions ld ON locald.id=ld.id WHERE l.localDescription.id IS NULL AND ld.id IS NULL")
    List<LocalDescription> getLocalsDescriptionNoRelationships();
}
