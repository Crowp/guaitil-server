package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.ProductReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
     @Query("SELECT pr FROM ProductReview pr  WHERE pr.productDescription.id =:id")
     Optional<ProductReview> selectProductReviewByProductId(@Param("id") Long id);

     @Query("SELECT pr FROM ProductReview pr INNER JOIN Product p ON pr.productDescription.id=p.productDescription.id" +
             " WHERE p.local.member.id =:id")
     Iterable<ProductReview> selectProductReviewByMemberId(@Param("id") Long memberId);

     @Query("SELECT m FROM Member m INNER JOIN m.locals l ON m.id=l.member.id " +
             "INNER JOIN l.products p ON l.id=p.local.id WHERE p.productDescription.id=:id ")
     Optional<Member> getMemberByProductDescId(@Param("id") Long productDescriptionId);
}
