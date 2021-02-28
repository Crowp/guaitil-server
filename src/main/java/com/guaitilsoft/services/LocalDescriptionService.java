package com.guaitilsoft.services;

import com.guaitilsoft.models.LocalDescription;

import java.util.List;

public interface LocalDescriptionService {

    List<LocalDescription> list();
    void deleteLocalsDescriptionsNotRelationships();
}
