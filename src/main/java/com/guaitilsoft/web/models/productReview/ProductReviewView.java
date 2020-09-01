package com.guaitilsoft.web.models.productReview;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.product.ProductView;
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

    private Date createdAt;

    private Date updatedAt;

    private ProductView product;
}
