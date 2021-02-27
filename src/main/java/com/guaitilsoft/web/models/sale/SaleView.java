package com.guaitilsoft.web.models.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.web.models.product.ProductLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleView {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleDate;

    private ProductLazyResponse product;

    private Long amountSold;
}
