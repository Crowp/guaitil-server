package com.guaitilsoft.web.models.productReview;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.ReviewState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductReviewView {

    private Long id;

    private Date reviewDate;

    private ReviewState state;

    private String comment;

    private Product product;
}
