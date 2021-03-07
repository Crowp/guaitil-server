package com.guaitilsoft.web.models.product;

import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.productDescription.ProductDescriptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductLazyResponse {
    private Long id;

    private Boolean status;

    private ProductDescriptionRequest productDescription;

    private List<MultimediaResponse> multimedia;
}
