package com.guaitilsoft.web.models.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleResponse {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime saleDate;

    private ProductDescriptionLazyResponse product;

    private Long amountSold;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
