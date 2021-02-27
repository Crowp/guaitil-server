package com.guaitilsoft.web.models.productReview;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.LoadProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GetProductReview {

    private Long id;

    private LocalDateTime reviewDate;

    private ReviewState state;

    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private LoadProduct product;

    public List<MultimediaResponse> getMultimedia(){
        return product.getMultimedia();
    }
}
