package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;

import java.util.List;

public interface LocalService {
    List<LocalResponse> list();

    LocalResponse get(Long id);

    LocalResponse save(LocalRequest entity);

    LocalResponse update(Long id, LocalRequest entity);

    LocalResponse updateShowLocal(Long id);

    void delete(Long id);

    List<LocalResponse> getAllLocalByIdMember(Long id);

    LocalResponse deleteMultimediaById(Long id, Long idMultimedia);

    List<LocalResponse> getLocalByLocalType(LocalType localType);

    List<Local> localList();
}
