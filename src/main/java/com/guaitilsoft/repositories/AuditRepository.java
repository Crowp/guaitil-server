package com.guaitilsoft.repositories;

import com.guaitilsoft.models.HistorialActividad;
import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<HistorialActividad, Long> {

}
