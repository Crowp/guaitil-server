package com.guaitilsoft.web.models.productReview;

import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.ProductRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductReviewView {

    private Long id;

    private ReviewState state;

    private String comment;

    private ProductRequest product;

    public List<MultimediaResponse> getMultimedia(){
        return product.getMultimedia();
    }
}
