package com.guaitilsoft.web.models.productReview;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductReviewResponse {

    private Long id;

    private LocalDateTime reviewDate;

    private ReviewState state;

    private String comment;

    private ProductDescriptionRequest productDescription;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
