package com.guaitilsoft.services;

import com.guaitilsoft.models.ProductReview;

import java.util.List;

public interface ProductReviewService {
    List<ProductReview> list();

    ProductReview get(Long id);

    void save(ProductReview entity);

    void update(Long id, ProductReview entity);

    void delete(Long id);
}
