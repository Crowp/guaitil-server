package com.guaitilsoft.web.models.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleRequest {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime saleDate;

    private ProductDescriptionId productDescription;

    private Long amountSold;
}
