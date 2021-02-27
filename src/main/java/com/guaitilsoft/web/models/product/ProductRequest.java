package com.guaitilsoft.web.models.product;

import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private Long id;

    private String name;

    private String description;

    private ProductType productType;

    private LocalId local;

    private ProductPrice productPrice;

    private List<MultimediaResponse> multimedia;
}
