package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;

import java.util.List;

public interface LocalRepositoryService {
    List<Local> list();

    Local get(Long id);

    Local getByLocalDescriptionId(Long localDescriptionId);

    Local save(Local entity);

    Local update(Long id, Local entity);

    void delete(Long id);

    List<Local> getAllLocalByIdMember(Long id);
}
