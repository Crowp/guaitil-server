package com.guaitilsoft.services;

import com.guaitilsoft.models.Associated;

import java.util.List;

public interface AssociatedService {
    List<Associated> list();

    Associated get(Long id);

    void save(Associated entity);

    void update(Long id,Associated entity);

    void delete(Long id);
}
