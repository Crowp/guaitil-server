package com.guaitilsoft.services;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.constant.LocalType;

import java.util.List;

public interface LocalService {
    List<Local> list();

    Local get(Long id);

    void save(Local entity);

    void update(Long id, Local entity);

    void delete(Long id);

    Local deleteMultimediaById(Long id, Long idMultimedia);

    List<Local> getAllLocalByIdMember(Long id);

    List<Local> getLocalByLocalType(LocalType localType);
}
