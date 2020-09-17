package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE s.product.local.member.id =:id")
    Iterable<Sale> getAllSaleByMemberId(@Param("id") Long id);
}
