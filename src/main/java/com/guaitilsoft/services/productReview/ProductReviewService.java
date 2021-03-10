package com.guaitilsoft.services.productReview;

import com.guaitilsoft.web.models.productReview.ProductReviewRequest;
import com.guaitilsoft.web.models.productReview.ProductReviewResponse;

import java.util.List;

public interface ProductReviewService {
    List<ProductReviewResponse> list();

    List<ProductReviewResponse> listByIdMember(Long memberId);

    ProductReviewResponse get(Long id);

    ProductReviewResponse save(ProductReviewRequest entity);

    ProductReviewResponse update(Long id, ProductReviewRequest entity);

    void delete(Long id);
}
