package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.ReviewState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.local.id =:id")
    Iterable<Product> getAllProductByLocalId(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.local.member.id =:id")
    Iterable<Product> getAllProductByMemberId(@Param("id") Long id);

    @Query("SELECT p FROM Product p INNER JOIN ProductReview pr ON pr.productDescription.id = p.productDescription.id WHERE pr.state =:reviewState AND p.local.id=:id AND p.showProduct=:showProduct")
    Iterable<Product> getAllProductAcceptedByLocalId(@Param("reviewState") ReviewState reviewState, @Param("id") Long id, @Param("showProduct") Boolean showProduct);

    @Query("SELECT p FROM Product p WHERE p.productDescription.id=:id")
    Optional<Product> getProductByProductDescriptionId(@Param("id")Long id);
}
