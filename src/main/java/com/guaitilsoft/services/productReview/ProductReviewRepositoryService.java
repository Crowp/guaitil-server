package com.guaitilsoft.services.productReview;

import com.guaitilsoft.models.ProductReview;

import java.util.List;

public interface ProductReviewRepositoryService {
    List<ProductReview> list();

    List<ProductReview> listByIdMember(Long memberId);

    ProductReview get(Long id);

    ProductReview getByProductId(Long productId);

    ProductReview save(ProductReview entity);

    ProductReview update(Long id, ProductReview entity);

    ProductReview updateReviewAndSendEmail(Long id, ProductReview entity);

    void delete(Long id);

    void deleteByProductDescriptionId(Long id);

    Boolean existsByProductDescriptionId(Long id);
}
