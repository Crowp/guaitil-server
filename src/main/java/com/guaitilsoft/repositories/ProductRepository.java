package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.local.id =:id")
    Iterable<Product> getAllProductByLocalId(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.local.member.id =:id")
    Iterable<Product> getAllProductByMemberId(@Param("id") Long id);
}
