package com.guaitilsoft.services;


import com.guaitilsoft.models.Tour;

import java.util.List;

public interface TourService {

    List<Tour> list();

    Tour get(Long id);

    void save(Tour entity);

    void update(Long id, Tour entity);

    void delete(Long id);
}
