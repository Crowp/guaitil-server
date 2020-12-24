package com.guaitilsoft.repositories;

import com.guaitilsoft.models.ProductReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
     @Query("SELECT p FROM ProductReview p WHERE p.product.id = :id")
     Optional<ProductReview> selectProductReviewByProductId(@Param("id") Long id);

     @Query("SELECT p FROM ProductReview p WHERE p.product.local.member.id = :id")
     Iterable<ProductReview> selectProductReviewByMemberId(@Param("id") Long memberId);
}
