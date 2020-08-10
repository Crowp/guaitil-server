package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Person;
import com.guaitilsoft.models.User;

import java.util.List;

public interface ActivityService {
    List<Activity> list();

    Activity get(Long id);

    void save(Activity entity);

    void update(Long id,Activity entity);

    void delete(Long id);
}
