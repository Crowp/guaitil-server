package com.guaitilsoft.web.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private Boolean status;

    private ProductType productType;

    private LocalLazyResponse local;

    private ProductPrice productPrice;

    private List<MultimediaResponse> multimedia;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
