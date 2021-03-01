package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ProductDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDescriptionRepository extends CrudRepository<ProductDescription, Long> {
    @Query("SELECT pd FROM ProductDescription pd LEFT JOIN Product p ON pd.id=p.productDescription.id " +
                                                "LEFT JOIN Sale s ON pd.id=s.productDescription.id " +
                                                "LEFT JOIN ProductReview pr ON pd.id=pr.productDescription.id " +
            "WHERE p.productDescription.id IS NULL AND s.productDescription.id IS NULL AND s.productDescription.id IS NULL")
    List<ProductDescription> getProductsDescriptionNoRelationships();
}

