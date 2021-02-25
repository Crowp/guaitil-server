package com.guaitilsoft.web.models.productReview;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.ProductView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductReviewView {

    private Long id;

    private ReviewState state;

    private String comment;

    private ProductView product;

    public List<MultimediaResponse> getMultimedia(){
        return product.getMultimedia();
    }
}
