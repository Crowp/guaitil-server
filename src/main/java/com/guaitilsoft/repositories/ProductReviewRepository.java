package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {

}
