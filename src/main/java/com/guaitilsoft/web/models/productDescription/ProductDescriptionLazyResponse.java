package com.guaitilsoft.web.models.productDescription;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDescriptionLazyResponse {

    private Long id;

    private String name;

    private String description;

    private ProductType productType;

    private ProductPrice productPrice;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
