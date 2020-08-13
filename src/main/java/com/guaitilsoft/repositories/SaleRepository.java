package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

}
