package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;

import java.util.List;

public interface LocalServiceLoad extends LocalRepositoryService {
    List<Local> resetPasswordByLocalId(Long id);
}
