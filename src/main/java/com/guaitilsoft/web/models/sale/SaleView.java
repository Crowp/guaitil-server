package com.guaitilsoft.web.models.sale;

import com.guaitilsoft.web.models.product.GetProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SaleView {

    private Long id;

    private Date saleDate;

    private GetProduct product;

    private Long amountSold;

    private Date createdAt;

    private Date updatedAt;
}
