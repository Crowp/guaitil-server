package com.guaitilsoft.services;

import com.guaitilsoft.models.ProductReview;

import java.util.List;

public interface ProductReviewService {
    List<ProductReview> list();

    List<ProductReview> listByIdMember(Long memberId);

    ProductReview get(Long id);

    ProductReview getByProductId(Long productId);

    void save(ProductReview entity);

    ProductReview update(Long id, ProductReview entity);

    void delete(Long id);

    void deleteProductReviewByProductId(Long productId);
}
