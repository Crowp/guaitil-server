package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
