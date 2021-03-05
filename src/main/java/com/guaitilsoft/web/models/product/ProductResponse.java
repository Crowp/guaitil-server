package com.guaitilsoft.web.models.product;

import com.guaitilsoft.models.ProductDescription;
import com.guaitilsoft.web.models.local.LocalLazyResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private Boolean status;

    private ProductDescription productDescription;

    private LocalLazyResponse local;

    private List<MultimediaResponse> multimedia;
}
