package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ProductPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long> {
}
