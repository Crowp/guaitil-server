package com.guaitilsoft.services.localDescription;

import com.guaitilsoft.models.LocalDescription;

import java.util.List;

public interface LocalDesRepositoryService {
    List<LocalDescription> list();
    LocalDescription get(Long id);
    List<LocalDescription> getLocalsDescriptionNoRelationships();
    void delete(Long id);
}
