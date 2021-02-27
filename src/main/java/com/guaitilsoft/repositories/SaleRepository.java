package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE s.productDescription.product.local.member.memberId =:id")
    Iterable<Sale> getAllSaleByMemberId(@Param("id") Long id);

    @Query("SELECT s FROM Sale s WHERE s.productDescription.product.id =:id")
    Optional<Sale> selectSaleByProductId(@Param("id") Long id);
}
