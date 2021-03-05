package com.guaitilsoft.web.models.productDescription;

import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductDescriptionRequest {

    private Long id;

    private String name;

    private String description;

    private ProductType productType;

    private ProductPrice productPrice;
}
