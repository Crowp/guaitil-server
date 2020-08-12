package com.guaitilsoft.services;

import com.guaitilsoft.models.Local;


import java.util.List;

public interface LocalService {
    List<Local> list();

    Local get(Long id);

    void save(Local entity);

    void update(Long id, Local entity);

    void delete(Long id);
}
