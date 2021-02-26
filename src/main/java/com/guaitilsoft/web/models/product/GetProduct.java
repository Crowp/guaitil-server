package com.guaitilsoft.web.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.LoadLocal;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GetProduct {
    private Long id;

    private String name;

    private String description;

    private Boolean status;

    private ProductType productType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private LoadLocal local;

    private ProductPrice productPrice;

    private List<MultimediaResponse> multimedia;
}
