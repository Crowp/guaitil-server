package com.guaitilsoft.web.models.productReview;

import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductReviewRequest {

    private Long id;

    private ReviewState state;

    private String comment;

    private ProductDescriptionRequest productDescription;
}
