package com.guaitilsoft.repositories;

import com.guaitilsoft.models.VirtualAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualAddressRepository extends CrudRepository<VirtualAddress,Long> {
}
