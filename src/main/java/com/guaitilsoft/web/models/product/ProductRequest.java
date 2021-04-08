package com.guaitilsoft.web.models.product;

import com.guaitilsoft.web.models.local.LocalId;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private Long id;

    private LocalId local;

    private ProductDescriptionRequest productDescription;

    private List<MultimediaResponse> multimedia;

    private Boolean showProduct = true;
}
